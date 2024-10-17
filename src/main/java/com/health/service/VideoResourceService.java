package com.health.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.health.model.VideoResource;
import com.opencsv.exceptions.CsvException;

public interface VideoResourceService {

    VideoResource findById(int id);

    VideoResource findByFileName(String fileName);

    VideoResource findByVideoPath(String videoPath);

    List<VideoResource> findByLangName(@Param("langName") String langName);

    VideoResource findByLangNameAndVideoPath(@Param("langName") String langName, @Param("videoPath") String videoPath);

    void saveVideoResourcesFromCSV(MultipartFile file, Model model)
            throws IOException, CsvException, NoSuchAlgorithmException;

    List<VideoResource> findAll();

}
