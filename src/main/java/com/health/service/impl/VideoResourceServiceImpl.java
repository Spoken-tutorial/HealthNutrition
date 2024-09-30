package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.VideoResource;
import com.health.repository.VideoResourceRepository;
import com.health.service.VideoResourceService;

@Service
public class VideoResourceServiceImpl implements VideoResourceService {

    @Autowired
    private VideoResourceRepository repo;

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
    public VideoResource findByLangNameAndVideoPath(String langName, String videoPath) {

        return repo.findByLangNameAndVideoPath(langName, videoPath);
    }

}
