package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.TopicLanMapping;
import com.health.model.TrainingResource;
import com.health.repository.TrainingResourceRepository;
import com.health.service.TrainingResourceService;

@Service
public class TrainingReourceServiceImpl implements TrainingResourceService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingReourceServiceImpl.class);

    @Autowired
    private TrainingResourceRepository repo;

    @Override
    public TrainingResource findByTrainingResourceId(int trainingResourceId) {

        return repo.findByTrainingResourceId(trainingResourceId);
    }

    @Override
    public List<TrainingResource> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(TrainingResource trainingResource) {
        repo.save(trainingResource);

    }

    @Override
    public void saveAll(List<TrainingResource> trainingResourceList) {
        repo.saveAll(trainingResourceList);

    }

    @Override
    public List<TrainingResource> findByTopicLanMapping(TopicLanMapping topicLanMapping) {

        return repo.findByTopicLanMapping(topicLanMapping);
    }

    @Override
    public List<TrainingResource> findByTopicLanMappingInAndStatusTrue(List<TopicLanMapping> topicLanMappingList) {

        return repo.findByTopicLanMappingInAndStatusTrue(topicLanMappingList);
    }

    @Override
    public List<TrainingResource> findAllByStatusTrue() {

        return repo.findAllByStatusTrue();
    }

}
