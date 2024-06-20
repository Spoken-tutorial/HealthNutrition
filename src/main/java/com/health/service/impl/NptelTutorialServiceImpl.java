package com.health.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.health.model.Language;
import com.health.model.NptelTutorial;
import com.health.model.PackageEntity;
import com.health.repository.NptelTutorialRepository;
import com.health.service.LanguageService;
import com.health.service.NptelTutorialService;
import com.health.utility.ServiceUtility;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@Service
public class NptelTutorialServiceImpl implements NptelTutorialService {
    private static final Logger logger = LoggerFactory.getLogger(NptelTutorialServiceImpl.class);

    @Autowired
    private NptelTutorialRepository nptelTutorialRepo;

    @Autowired
    private LanguageService lanService;

    @Override
    public NptelTutorial findById(int id) {
        try {
            Optional<NptelTutorial> local = nptelTutorialRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error("find By Id error in LiveTutorial Service Impl class: {}", id, e);
            return null;
        }
    }

    @Override
    public List<NptelTutorial> findAll() {
        return nptelTutorialRepo.findAll();
    }

    @Override
    public void saveNptelTutorialsFromCSV(MultipartFile file, Model model, PackageEntity packageEntity)
            throws IOException, CsvException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReader(reader)) {

            List<NptelTutorial> nptelTutorialsList = new ArrayList<>();
            List<String> errorMessages = new ArrayList<>();

            String[] header = csvReader.readNext();
            int titleColumn = -1;
            int weekColumn = -1;
            int languageColumn = -1;
            int urlColumn = -1;

            for (int i = 0; i < header.length; i++) {
                switch (header[i]) {
                case "Title":
                    titleColumn = i;
                    break;
                case "Week":
                    weekColumn = i;
                    break;
                case "Language":
                    languageColumn = i;
                    break;
                case "Url":
                    urlColumn = i;
                    break;
                }
            }

            if (titleColumn == -1 || weekColumn == -1 || languageColumn == -1 || urlColumn == -1) {
                model.addAttribute("error_msg",
                        "Some Errors Occurred; missing header for title, week, language, or url");
                return;
            }

            Map<String, List<Integer>> titleWeekLanguageMap = new HashMap<>();
            Map<String, List<Integer>> urlMap = new HashMap<>();
            String[] row;
            int rowIndex = 1; // To track the row number for error reporting

            while ((row = csvReader.readNext()) != null) {

                String titleWeekLanguage = row[titleColumn] + "-" + row[weekColumn] + "-" + row[languageColumn];
                String url = row[urlColumn];

                titleWeekLanguageMap.computeIfAbsent(titleWeekLanguage, k -> new ArrayList<>()).add(rowIndex);
                urlMap.computeIfAbsent(url, k -> new ArrayList<>()).add(rowIndex);

                rowIndex++;

                if (!processRow(row, titleColumn, weekColumn, languageColumn, urlColumn, packageEntity, model,
                        nptelTutorialsList)) {
                    continue;
                }
            }

            // Check for duplicates rows for title, week, and language. And add error
            // messages
            for (Map.Entry<String, List<Integer>> entry : titleWeekLanguageMap.entrySet()) {
                if (entry.getValue().size() > 1) {
                    errorMessages.add("Duplicate title, week, and language at S. No. " + entry.getValue());
                }
            }

            // Check for duplicates rows for same url. And add error messages
            for (Map.Entry<String, List<Integer>> entry : urlMap.entrySet()) {
                if (entry.getValue().size() > 1) {
                    errorMessages.add("Duplicate URL at S. No. " + entry.getValue());
                }
            }

            if (!errorMessages.isEmpty()) {
                model.addAttribute("error_msg", String.join("; ", errorMessages));
                return;
            }

            logger.info("Size of nptelTutorialsList: {}", nptelTutorialsList.size());

            nptelTutorialRepo.saveAll(nptelTutorialsList);

            model.addAttribute("success_msg", "Saved liveTutorialList:  " + nptelTutorialsList.size());
        }
    }

    private boolean processRow(String[] row, int titleColumn, int weekColumn, int languageColumn, int urlColumn,
            PackageEntity packageEntity, Model model, List<NptelTutorial> nptelTutorialsList) {
        logger.info("Processing row: {}", Arrays.toString(row));

        Language lan = lanService.getByLanName(row[languageColumn]);
        if (lan == null) {
            model.addAttribute("error_msg", "Could not get language from " + row[languageColumn]);
            return false;
        }

        logger.info(row[urlColumn]);

        /*
         * add error message if the url of the existing tutorial in database does not
         * exist for the same package. if it was found for same package then it is going
         * to update otherwise it will show error because url is unique
         */
        NptelTutorial temp = nptelTutorialRepo.findByVideoUrl(row[urlColumn]);
        if (temp != null) {
            if (temp.getPackageEntity().getPackageId() != packageEntity.getPackageId())
                model.addAttribute("error_msg", "This url alreday exists in the nptelTutorial " + row[urlColumn]);
            return false;
        }

        NptelTutorial nptelTutorial = nptelTutorialRepo.findByTitleAndPackageEntityAndLanAndWeek(row[titleColumn],
                packageEntity, lan, Integer.parseInt(row[weekColumn]));
        if (nptelTutorial == null) {
            nptelTutorial = new NptelTutorial();
        }
        nptelTutorial.setTitle(row[titleColumn]);
        nptelTutorial.setWeek(Integer.parseInt(row[weekColumn]));
        nptelTutorial.setPackageEntity(packageEntity);
        nptelTutorial.setLan(lan);
        nptelTutorial.setVideoUrl(row[urlColumn]);
        nptelTutorial.setDateAdded(ServiceUtility.getCurrentTime());
        nptelTutorialsList.add(nptelTutorial);

        return true;
    }

}
