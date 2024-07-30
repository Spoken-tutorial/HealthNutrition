package com.health.service;

import java.io.IOException;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.health.model.NptelTutorial;
import com.opencsv.exceptions.CsvException;

public interface NptelTutorialService {

    NptelTutorial findById(int id);

    List<NptelTutorial> findAll();

    void saveNptelTutorialsFromCSV(MultipartFile file, Model model, String packageName)
            throws IOException, CsvException;

}
