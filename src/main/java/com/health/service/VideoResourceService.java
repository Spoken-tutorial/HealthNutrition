package com.health.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.health.model.VideoResource;

public interface VideoResourceService {

    VideoResource findById(int id);

    VideoResource findByFileName(String fileName);

    VideoResource findByVideoPath(String videoPath);

    List<VideoResource> findByLangName(@Param("langName") String langName);

    VideoResource findByLangNameAndVideoPath(@Param("langName") String langName, @Param("videoPath") String videoPath);

}
