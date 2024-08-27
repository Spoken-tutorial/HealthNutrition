package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.SpokenVideo;
import com.health.repository.SpokenVideoRepository;
import com.health.service.SpokenVideoService;

@Service
public class SpokenVideoServiceImpl implements SpokenVideoService {

    private static final Logger logger = LoggerFactory.getLogger(SpokenVideoServiceImpl.class);

    @Autowired
    private SpokenVideoRepository repo;

    @Override
    public void save(SpokenVideo temp) {
        repo.save(temp);

    }

    @Override
    public int getNewSpokenVideoId() {

        try {
            Integer newId = repo.getNewId();
            return (newId != null ? newId : 0) + 1;
        } catch (Exception e) {

            logger.error("New Id error in SpokenVideo  Service Impl: {}", repo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public List<SpokenVideo> findAll() {

        return (List<SpokenVideo>) repo.findAll();
    }

    @Override
    public void delete(SpokenVideo temp) {
        repo.delete(temp);

    }

    @Override
    public SpokenVideo findById(int id) {

        return repo.findById(id).get();
    }

    @Override
    public SpokenVideo findByDisplayName(String displayName) {

        return repo.findByDisplayName(displayName);
    }

    @Override
    public SpokenVideo findByFilePath(String filePath) {
        return repo.findByFilePath(filePath);

    }

}
