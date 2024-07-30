package com.health.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.health.model.Language;
import com.health.model.NptelTutorial;
import com.health.model.PackLanWeekMapping;
import com.health.model.PackageEntity;
import com.health.model.PackageLanMapping;
import com.health.model.Week;
import com.health.repository.NptelTutorialRepository;
import com.health.repository.PackLanWeekRepository;
import com.health.repository.PackageEntityReopository;
import com.health.repository.PackageLanRepository;
import com.health.repository.WeekRepository;
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
    private WeekRepository weekRepo;

    @Autowired
    private PackageEntityReopository packageRepo;

    @Autowired
    private PackageLanRepository packLanRepo;

    @Autowired
    private PackLanWeekRepository packLanWeekRepo;

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

//    @Override
//    public void saveNptelTutorialsFromCSV(MultipartFile file, Model model, String packageName)
//            throws IOException, CsvException {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
//                CSVReader csvReader = new CSVReader(reader)) {
//            List<NptelTutorial> nptelTutorialsList = new ArrayList<>();
//            List<Week> weekList = new ArrayList<>();
//            List<PackageLanMapping> packLanList = new ArrayList<>();
//            List<PackLanWeekMapping> packLanWeekkList = new ArrayList<>();
//            PackageEntity packageEntity = packageRepo.findByPackageName(packageName);
//            if (packageEntity == null) {
//                packageEntity = new PackageEntity();
//                packageEntity.setPackageName(packageName);
//                packageEntity.setDateAdded(ServiceUtility.getCurrentTime());
//                packageRepo.save(packageEntity);
//
//            }
//
//            String[] header = csvReader.readNext();
//            int titlecolumn = -1;
//            int weekcolumn = -1;
//            int languagecolumn = -1;
//            int urlcolumn = -1;
//
//            for (int i = 0; i < header.length; i++) {
//
//                switch (header[i]) {
//                case "Title":
//                    titlecolumn = i;
//                    break;
//
//                case "Week":
//                    weekcolumn = i;
//                    break;
//
//                case "Language":
//                    languagecolumn = i;
//                    break;
//
//                case "Url":
//                    urlcolumn = i;
//                    break;
//
//                }
//            }
//
//            if (titlecolumn == -1 || weekcolumn == -1 || languagecolumn == -1 || urlcolumn == -1) {
//                model.addAttribute("error_msg",
//                        "Some Errors Occured; missing header or name or title or language or url");
//                return;
//            }
//
//            String[] row;
//            while ((row = csvReader.readNext()) != null) {
//                NptelTutorial nptelTutorial = nptelTutorialRepo.findByTitle(row[titlecolumn]);
//                if (nptelTutorial == null) {
//                    nptelTutorial = new NptelTutorial();
//                    logger.info(row[titlecolumn]);
//                    nptelTutorial.setTitle(row[titlecolumn]);
//                }
//
//                logger.info(row[weekcolumn]);
//                Week week = weekRepo.findByWeekName(row[weekcolumn]);
//                PackLanWeekMapping packLanWeek = new PackLanWeekMapping();
//                PackageLanMapping packLan = new PackageLanMapping();
//                if (week == null) {
//
//                    week = new Week();
//                    week.setWeekName(row[weekcolumn]);
//                    week.setDateAdded(ServiceUtility.getCurrentTime());
//                }
//                packLanWeek.setWeek(week);
//
//                logger.info(row[languagecolumn]);
//                Language lan = lanService.getByLanName(row[languagecolumn]);
//                if (lan == null) {
//                    model.addAttribute("error_msg", "Could not get language from " + row[languagecolumn]);
//                    continue;
//                }
//                packLan.setLan(lan);
//                packLan.setPackage1(packageEntity);
//                packLanWeek.setPackageLan(packLan);
//                packLanWeek.setWeek(week);
//                packLanWeek.setDateAdded(ServiceUtility.getCurrentTime());
//                nptelTutorial.setPackLanWeek(packLanWeek);
//
//                logger.info(row[urlcolumn]);
//                String url = nptelTutorialRepo.findByVideoUrl(row[urlcolumn]);
//                if (url != null) {
//                    model.addAttribute("error_msg", "This url alreday exists in the nptelTutorial " + row[urlcolumn]);
//                    continue;
//                }
//                nptelTutorial.setVideoUrl(row[urlcolumn]);
//                nptelTutorial.setDateAdded(ServiceUtility.getCurrentTime());
//                nptelTutorialsList.add(nptelTutorial);
//                weekList.add(week);
//                packLanList.add(packLan);
//                packLanWeekkList.add(packLanWeek);
//
//            }
//            logger.info("Size of liveTutorialsList: {}", nptelTutorialsList.size());
//            weekRepo.saveAll(weekList);
//            packLanRepo.saveAll(packLanList);
//            packLanWeekRepo.saveAll(packLanWeekkList);
//            nptelTutorialRepo.saveAll(nptelTutorialsList);
//
//            model.addAttribute("success_msg", "Saved liveTutorialList:  " + nptelTutorialsList.size());
//        }
//    }

    @Override
    public void saveNptelTutorialsFromCSV(MultipartFile file, Model model, String packageName)
            throws IOException, CsvException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReader(reader)) {

            List<NptelTutorial> nptelTutorialsList = new ArrayList<>();
            List<Week> weekList = new ArrayList<>();
            List<PackageLanMapping> packLanList = new ArrayList<>();
            List<PackLanWeekMapping> packLanWeekkList = new ArrayList<>();

            PackageEntity packageEntity = packageRepo.findByPackageName(packageName);
            if (packageEntity == null) {
                packageEntity = new PackageEntity();
                packageEntity.setPackageName(packageName);
                packageEntity.setDateAdded(ServiceUtility.getCurrentTime());
                packageRepo.save(packageEntity);
            }

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

            String[] row;
            while ((row = csvReader.readNext()) != null) {
                if (!processRow(row, titleColumn, weekColumn, languageColumn, urlColumn, packageEntity, model,
                        nptelTutorialsList)) {
                    continue;
                }
            }

            logger.info("Size of nptelTutorialsList: {}", nptelTutorialsList.size());
            weekRepo.saveAll(weekList);
            packLanRepo.saveAll(packLanList);
            packLanWeekRepo.saveAll(packLanWeekkList);
            nptelTutorialRepo.saveAll(nptelTutorialsList);

            model.addAttribute("success_msg", "Saved liveTutorialList:  " + nptelTutorialsList.size());
        }
    }

    private boolean processRow(String[] row, int titleColumn, int weekColumn, int languageColumn, int urlColumn,
            PackageEntity packageEntity, Model model, List<NptelTutorial> nptelTutorialsList) {
        logger.info("Processing row: {}", Arrays.toString(row));

        NptelTutorial nptelTutorial = nptelTutorialRepo.findByTitle(row[titleColumn]);
        if (nptelTutorial == null) {
            nptelTutorial = new NptelTutorial();
            nptelTutorial.setTitle(row[titleColumn]);
        }

        Week week = weekRepo.findByWeekName(row[weekColumn]);

        if (week == null) {
            week = new Week();
            week.setWeekName(row[weekColumn]);
            week.setDateAdded(ServiceUtility.getCurrentTime());
            weekRepo.save(week);
        }

        Language lan = lanService.getByLanName(row[languageColumn]);
        if (lan == null) {
            model.addAttribute("error_msg", "Could not get language from " + row[languageColumn]);
            return false;
        }

        NptelTutorial tempTutorial = nptelTutorialRepo.findByVideoUrl(row[urlColumn]);
        if (tempTutorial != null) {
            model.addAttribute("error_msg", "This url already exists in the nptelTutorial " + row[urlColumn]);
            return false;
        }

        PackageLanMapping packLan = packLanRepo.findByPackageAndlan(packageEntity, lan);
        PackLanWeekMapping packLanWeek = null;

        if (packLan != null) {
            packLanWeek = packLanWeekRepo.findByPackageLanAndWeek(packLan, week);
        } else {
            packLan = new PackageLanMapping();
            packLan.setLan(lan);
            packLan.setPackage1(packageEntity);
            packLanRepo.save(packLan);
        }

        if (packLanWeek == null) {
            packLanWeek = new PackLanWeekMapping();
            packLanWeek.setPackageLan(packLan);
            packLanWeek.setWeek(week);
            packLanWeek.setDateAdded(ServiceUtility.getCurrentTime());
            packLanWeekRepo.save(packLanWeek);
        }

        nptelTutorial.setPackLanWeek(packLanWeek);

        nptelTutorial.setVideoUrl(row[urlColumn]);
        nptelTutorial.setDateAdded(ServiceUtility.getCurrentTime());
        nptelTutorialsList.add(nptelTutorial);

        return true;
    }

}
