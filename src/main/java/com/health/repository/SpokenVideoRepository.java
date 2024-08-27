package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.SpokenVideo;

public interface SpokenVideoRepository extends CrudRepository<SpokenVideo, Integer> {

    SpokenVideo findByDisplayName(String displayName);

    SpokenVideo findByFilePath(String filePath);

    @Query("select max(spokenVideoId) from SpokenVideo")
    Integer getNewId();

}
