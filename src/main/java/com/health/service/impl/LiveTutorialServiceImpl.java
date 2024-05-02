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
    public int getNewId() {
        try {
            if (repo.getNewId() == null)
                return 1;
            else
                return repo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in LiveTutorial Service Impl class: {}", repo.getNewId(), e);
            return 1;
        }
    }

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
    public void saveLiveTutorialsFromCSV(MultipartFile file) throws IOException, CsvException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReader(reader)) {
            List<LiveTutorial> liveTutorialsList = new ArrayList<>();
            List<String[]> rows = csvReader.readAll();
            int liveTutorialId = getNewId();

            for (String[] row : rows) {
                LiveTutorial liveTutorial = new LiveTutorial();
                liveTutorial.setId(liveTutorialId);
                logger.info(row[0]);
                liveTutorial.setName(row[0]);
                logger.info(row[1]);
                liveTutorial.setTitle(row[1]);
                logger.info(row[2]);
                Language lan = lanService.getByLanName(row[2]);
                liveTutorial.setLan(lan);
                logger.info(row[3]);
                liveTutorial.setUrl(row[3]);
                liveTutorial.setDateAdded(ServiceUtility.getCurrentTime());
                liveTutorialsList.add(liveTutorial);
                liveTutorialId += 1;

            }
            logger.info("Size of liveTutorialsList: {}", liveTutorialsList.size());
            repo.saveAll(liveTutorialsList);
        }
    }

}
