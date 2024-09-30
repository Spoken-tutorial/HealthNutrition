package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.VideoResource;

public interface VideoResourceRepository extends JpaRepository<VideoResource, Integer> {

    VideoResource findById(int id);

    VideoResource findByFileName(String fileName);

    VideoResource findByVideoPath(String videoPath);

    @Query("SELECT vr FROM VideoResource vr WHERE vr.lan.langName = :langName")
    List<VideoResource> findByLangName(@Param("langName") String langName);

    @Query("SELECT vr FROM VideoResource vr WHERE vr.lan.langName = :langName AND vr.videoPath = :videoPath")
    VideoResource findByLangNameAndVideoPath(@Param("langName") String langName, @Param("videoPath") String videoPath);

}
