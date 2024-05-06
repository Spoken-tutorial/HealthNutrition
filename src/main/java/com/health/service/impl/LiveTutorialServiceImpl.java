package com.health.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.health.model.Language;
import com.health.model.LiveTutorial;
import com.health.repository.LiveTutorialRepository;
import com.health.service.LanguageService;
import com.health.service.LiveTutorialService;
import com.health.utility.ServiceUtility;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@Service
public class LiveTutorialServiceImpl implements LiveTutorialService {
    private static final Logger logger = LoggerFactory.getLogger(LiveTutorialServiceImpl.class);

    @Autowired
    private LiveTutorialRepository repo;

    @Autowired
    private LanguageService lanService;

    @Override
    public LiveTutorial findById(int id) {
        try {
            Optional<LiveTutorial> local = repo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error("find By Id error in LiveTutorial Service Impl class: {}", id, e);
            return null;
        }
    }

    @Override
    public List<LiveTutorial> findAll() {
        return (List<LiveTutorial>) repo.findAll();
    }

    @Override
    public void saveLiveTutorialsFromCSV(MultipartFile file, Model model) throws IOException, CsvException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReader(reader)) {
            List<LiveTutorial> liveTutorialsList = new ArrayList<>();
            String[] header = csvReader.readNext();
            int namecolumn = -1;
            int titlecolumn = -1;
            int languagecolumn = -1;
            int urlcolumn = -1;

            for (int i = 0; i < header.length; i++) {

                switch (header[i]) {
                case "Name":
                    namecolumn = i;
                    break;

                case "Title":
                    titlecolumn = i;
                    break;

                case "Language":
                    languagecolumn = i;
                    break;

                case "Url":
                    urlcolumn = i;
                    break;

                }
            }

            if (namecolumn == -1 || titlecolumn == -1 || languagecolumn == -1 || urlcolumn == -1) {
                model.addAttribute("error_msg",
                        "Some Errors Occured; missing header or name or title or language or url");
                return;
            }
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                LiveTutorial liveTutorial = repo.findByName(row[namecolumn]);
                if (liveTutorial == null) {
                    liveTutorial = new LiveTutorial();
                    logger.info(row[namecolumn]);
                    liveTutorial.setName(row[namecolumn]);
                }

                logger.info(row[titlecolumn]);
                liveTutorial.setTitle(row[titlecolumn]);
                logger.info(row[languagecolumn]);
                Language lan = lanService.getByLanName(row[languagecolumn]);
                if (lan == null) {
                    model.addAttribute("error_msg", "Could not get language from " + row[namecolumn]);
                    continue;
                }
                liveTutorial.setLan(lan);
                logger.info(row[urlcolumn]);
                liveTutorial.setUrl(row[urlcolumn]);
                liveTutorial.setDateAdded(ServiceUtility.getCurrentTime());
                liveTutorialsList.add(liveTutorial);

            }
            logger.info("Size of liveTutorialsList: {}", liveTutorialsList.size());

            repo.saveAll(liveTutorialsList);
            model.addAttribute("success_msg", "Saved liveTutorialList:  " + liveTutorialsList.size());
        }
    }

}
