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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.health.model.Language;
import com.health.model.VideoResource;
import com.health.repository.VideoResourceRepository;
import com.health.service.LanguageService;
import com.health.service.VideoResourceService;
import com.health.utility.CommonData;
import com.health.utility.ServiceUtility;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvException;

@Service
public class VideoResourceServiceImpl implements VideoResourceService {

    private static final Logger logger = LoggerFactory.getLogger(VideoResourceServiceImpl.class);

    @Autowired
    private VideoResourceRepository repo;

    @Autowired
    private LanguageService lanService;

    @Autowired
    private Environment env;

    @Override
    public VideoResource findById(int id) {

        return repo.findById(id);
    }

    @Override
    public VideoResource findByFileName(String fileName) {

        return repo.findByFileName(fileName);
    }

    @Override
    public VideoResource findByVideoPath(String videoPath) {

        return repo.findByVideoPath(videoPath);
    }

    @Override
    public List<VideoResource> findByLangName(String langName) {

        return repo.findByLangName(langName);
    }

    @Override
    public List<VideoResource> findAll() {

        return repo.findAll();
    }

    @Override
    public VideoResource findByLangNameAndVideoPath(String langName, String videoPath) {

        return repo.findByLangNameAndVideoPath(langName, videoPath);
    }

    @Override
    public void saveVideoResourcesFromCSV(MultipartFile file, Model model)
            throws IOException, CsvException, NoSuchAlgorithmException {
        RFC4180Parser parser = new RFC4180ParserBuilder().build();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()) {
            List<VideoResource> videoResourceList = new ArrayList<>();
            List<String> errorMessages = new ArrayList<>();

            Path tempDir = Files.createTempDirectory("");
            File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
            file.transferTo(tempFile);

            String[] header = csvReader.readNext();
            int fileNameColumn = -1;
            int thumbnailColumn = -1;

            int languageColumn = -1;

            for (int i = 0; i < header.length; i++) {
                switch (header[i]) {
                case "FileName":
                    fileNameColumn = i;
                    break;
                case "Thumbnail":
                    thumbnailColumn = i;
                    break;
                case "Language":
                    languageColumn = i;
                    break;

                }
            }

            if (fileNameColumn == -1 || languageColumn == -1 || thumbnailColumn == -1) {
                model.addAttribute("error_msg",
                        "Some Errors Occurred; missing header   FileName , thumnail,or language");
                return;
            }

            Map<String, List<Integer>> fileNameLanguageMap = new HashMap<>();
            Map<String, List<Integer>> urlMap = new HashMap<>();

            String[] row;
            int rowIndex = 1; // To track the row number for error reporting

            while ((row = csvReader.readNext()) != null) {

                String fileNameThumbnailLanguage = row[fileNameColumn] + "-" + row[thumbnailColumn] + "-"
                        + row[languageColumn];

                fileNameLanguageMap.computeIfAbsent(fileNameThumbnailLanguage, k -> new ArrayList<>()).add(rowIndex);

                rowIndex++;

                if (!processRow(row, languageColumn, fileNameColumn, thumbnailColumn, model, videoResourceList)) {
                    continue;
                }
            }

            // Check for duplicates rows for same url. And add error messages
            for (Map.Entry<String, List<Integer>> entry : fileNameLanguageMap.entrySet()) {
                if (entry.getValue().size() > 1) {
                    errorMessages.add("Duplicate url at S. No. " + entry.getValue());
                }
            }

            if (!errorMessages.isEmpty()) {
                model.addAttribute("error_msg", String.join("; ", errorMessages));
                return;
            }

            logger.info("Size of videoResourceList: {}", videoResourceList.size());

            repo.saveAll(videoResourceList);

            model.addAttribute("success_msg", "Saved videoResourceList:  " + videoResourceList.size());
        }
    }

    private boolean processRow(String[] row, int languageColumn, int fileNameColumn, int thumbnailColumn, Model model,
            List<VideoResource> videoResourceList) {
        logger.info("Processing row: {}", Arrays.toString(row));

        Language lan = lanService.getByLanName(row[languageColumn]);
        if (lan == null) {
            model.addAttribute("error_msg", "Could not get language from " + row[languageColumn]);
            return false;
        }
        String langName = lan.getLangName();
        String fileName = row[fileNameColumn];
        String thumbnailName = row[thumbnailColumn];
        Path path = Paths.get(env.getProperty("spring.applicationexternalPath.name"), CommonData.uploadDirectorySource,
                langName, fileName);
        String temp = path.toString();
        int indexToStart = temp.indexOf("Media");
        String filePath = temp.substring(indexToStart, temp.length());
        logger.info(filePath);

        Path path1 = Paths.get(env.getProperty("spring.applicationexternalPath.name"), CommonData.uploadDirectorySource,
                langName, "Thumbnail", thumbnailName);
        String temp1 = path1.toString();
        int indexToStart1 = temp1.indexOf("Media");
        String tempThumbnail = temp1.substring(indexToStart1, temp1.length());

        String thumnailPath = ServiceUtility.convertFilePathToUrl(tempThumbnail);
        logger.info(thumnailPath);

        if (!Files.exists(path)) {
            model.addAttribute("error_msg", "File does not exist; fileName : " + row[fileNameColumn]);
            return false;
        }

        File thumbnailFile = path1.toFile();

        VideoResource videoResource = repo.findByVideoPath(filePath);
        if (videoResource == null) {
            videoResource = new VideoResource();
            videoResource.setVideoPath(filePath);

            videoResource.setFileName(fileName);
            videoResource.setLan(lan);
            if (thumbnailFile.exists())
                videoResource.setThumbnailPath(thumnailPath);
            videoResource.setDateAdded(ServiceUtility.getCurrentTime());
            videoResourceList.add(videoResource);
        }

        else {
            if (thumbnailFile.exists()) {
                videoResource.setThumbnailPath(thumnailPath);
                videoResource.setDateAdded(ServiceUtility.getCurrentTime());
                videoResourceList.add(videoResource);
            }
        }

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
