package com.health.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.health.model.NptelTutorial;
import com.health.model.PackageEntity;
import com.opencsv.exceptions.CsvException;

public interface NptelTutorialService {

    NptelTutorial findById(int id);

    List<NptelTutorial> findAll();

    void saveNptelTutorialsFromCSV(MultipartFile file, Model model, PackageEntity packageEntity)
            throws IOException, CsvException, NoSuchAlgorithmException;

}
