package com.health.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.health.model.Language;
import com.health.model.NptelTutorial;
import com.health.model.PackageEntity;
import com.health.repository.NptelTutorialRepository;
import com.health.repository.PackageEntityReopository;
import com.health.service.LanguageService;
import com.health.service.NptelTutorialService;
import com.health.utility.CommonData;
import com.health.utility.ServiceUtility;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvException;

@Service
public class NptelTutorialServiceImpl implements NptelTutorialService {
    private static final Logger logger = LoggerFactory.getLogger(NptelTutorialServiceImpl.class);

    @Autowired
    private NptelTutorialRepository nptelTutorialRepo;

    @Autowired
    private PackageEntityReopository packageEntityRepo;

    @Autowired
    private LanguageService lanService;

    @Autowired
    private Environment env;

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
            throws IOException, CsvException, NoSuchAlgorithmException {
        RFC4180Parser parser = new RFC4180ParserBuilder().build();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()) {
            List<NptelTutorial> nptelTutorialsList = new ArrayList<>();
            List<String> errorMessages = new ArrayList<>();

            Path tempDir = Files.createTempDirectory("");
            File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
            file.transferTo(tempFile);
            String checksum = getSHA256Checksum(tempFile);
            String csvFileName = file.getOriginalFilename();
            PackageEntity tempPackageEntity = packageEntityRepo.findByChecksum(checksum);
            if (tempPackageEntity == null) {
                tempPackageEntity = packageEntityRepo.findByFileName(csvFileName);
            }
            if (tempPackageEntity != null) {
                if (tempPackageEntity.getPackageId() != packageEntity.getPackageId()) {
                    model.addAttribute("error_msg", "Same csv file alreday exists for the other package ");
                    return;
                }
            }
            String[] header = csvReader.readNext();
            int titleColumn = -1;
            int weekColumn = -1;
            int languageColumn = -1;
            int fileNameColumn = -1;

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
                case "FileName":
                    fileNameColumn = i;
                    break;
                }
            }

            if (titleColumn == -1 || weekColumn == -1 || languageColumn == -1 || fileNameColumn == -1) {
                model.addAttribute("error_msg",
                        "Some Errors Occurred; missing header for title, week, language, or FileName");
                return;
            }

            Map<String, List<Integer>> titleLanguageMap = new HashMap<>();
            Map<String, List<Integer>> urlMap = new HashMap<>();

            String[] row;
            int rowIndex = 1; // To track the row number for error reporting

            while ((row = csvReader.readNext()) != null) {

                String titleLanguage = row[titleColumn] + "-" + row[languageColumn];
                String fileName = row[fileNameColumn];

                titleLanguageMap.computeIfAbsent(titleLanguage, k -> new ArrayList<>()).add(rowIndex);
                urlMap.computeIfAbsent(fileName, k -> new ArrayList<>()).add(rowIndex);

                rowIndex++;

                if (!processRow(row, titleColumn, weekColumn, languageColumn, fileNameColumn, packageEntity, model,
                        nptelTutorialsList)) {
                    continue;
                }
            }

            // Check for duplicates rows for title, week, and language. And add error
            // messages
            for (Map.Entry<String, List<Integer>> entry : titleLanguageMap.entrySet()) {
                if (entry.getValue().size() > 1) {
                    errorMessages.add("Duplicate title and language at S. No. " + entry.getValue());
                }
            }

            // Check for duplicates rows for same url. And add error messages
            for (Map.Entry<String, List<Integer>> entry : urlMap.entrySet()) {
                if (entry.getValue().size() > 1) {
                    errorMessages.add("Duplicate language and fileName at S. No. " + entry.getValue());
                }
            }

            if (!errorMessages.isEmpty()) {
                model.addAttribute("error_msg", String.join("; ", errorMessages));
                return;
            }

            logger.info("Size of nptelTutorialsList: {}", nptelTutorialsList.size());

            nptelTutorialRepo.saveAll(nptelTutorialsList);

            packageEntity.setDateUploaded(ServiceUtility.getCurrentTime());
            packageEntity.setFileName(csvFileName);
            packageEntity.setChecksum(checksum);
            packageEntityRepo.save(packageEntity);

            model.addAttribute("success_msg", "Saved liveTutorialList:  " + nptelTutorialsList.size());
        }
    }

    private boolean processRow(String[] row, int titleColumn, int weekColumn, int languageColumn, int fileNameColumn,
            PackageEntity packageEntity, Model model, List<NptelTutorial> nptelTutorialsList) {
        logger.info("Processing row: {}", Arrays.toString(row));

        Language lan = lanService.getByLanName(row[languageColumn]);
        if (lan == null) {
            model.addAttribute("error_msg", "Could not get language from " + row[languageColumn]);
            return false;
        }
        String langName = lan.getLangName();
        String fileName = row[fileNameColumn];
        Path path = Paths.get(env.getProperty("spring.applicationexternalPath.name"), CommonData.uploadDirectorySource,
                langName, fileName);
        String temp = path.toString();
        int indexToStart = temp.indexOf("Media");
        String filePath = temp.substring(indexToStart, temp.length());
        logger.info(filePath);

        if (!Files.exists(path)) {
            model.addAttribute("error_msg", "File does not exist; fileName : " + row[fileNameColumn]);
            return false;
        }

        NptelTutorial nptelTutorial = nptelTutorialRepo.findByTitleAndPackageEntityAndLan(row[titleColumn],
                packageEntity, lan);
        if (nptelTutorial == null) {
            nptelTutorial = nptelTutorialRepo.findByVideoUrlAndPackageEntityAndLan(filePath, packageEntity, lan);
            if (nptelTutorial == null) {
                nptelTutorial = new NptelTutorial();
                nptelTutorial.setPackageEntity(packageEntity);
                nptelTutorial.setLan(lan);
            }

        }
        nptelTutorial.setTitle(row[titleColumn]);
        nptelTutorial.setWeek(Integer.parseInt(row[weekColumn]));

        nptelTutorial.setVideoUrl(filePath);
        nptelTutorial.setDateAdded(ServiceUtility.getCurrentTime());
        nptelTutorial.setFileName(fileName);
        nptelTutorialsList.add(nptelTutorial);

        return true;
    }

    private String getSHA256Checksum(File file) throws NoSuchAlgorithmException, IOException {
        FileInputStream fis = new FileInputStream(file);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        fis.close();

        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
