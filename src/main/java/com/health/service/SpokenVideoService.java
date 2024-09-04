package com.health.service;

import java.util.List;

import com.health.model.SpokenVideo;

public interface SpokenVideoService {

    void save(SpokenVideo temp);

    List<SpokenVideo> findAll();

    void delete(SpokenVideo temp);

    SpokenVideo findById(int id);

    SpokenVideo findByDisplayName(String displayName);

    SpokenVideo findByFilePath(String filePath);

}
