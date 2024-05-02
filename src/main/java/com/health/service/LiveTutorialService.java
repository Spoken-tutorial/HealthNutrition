package com.health.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.health.model.LiveTutorial;
import com.opencsv.exceptions.CsvException;

public interface LiveTutorialService {

    int getNewId();

    LiveTutorial findById(int id);

    List<LiveTutorial> findAll();

    void saveLiveTutorialsFromCSV(MultipartFile file) throws IOException, CsvException;
}
