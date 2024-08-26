package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.health.model.SpokenVideo;
import com.health.repository.SpokenVideoRepository;
import com.health.service.SpokenVideoService;

public class SpokenVideoServiceImpl implements SpokenVideoService {

    private static final Logger logger = LoggerFactory.getLogger(SpokenVideoServiceImpl.class);

    @Autowired
    private SpokenVideoRepository repo;

    @Override
    public void save(SpokenVideo temp) {
        repo.save(temp);

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

}
