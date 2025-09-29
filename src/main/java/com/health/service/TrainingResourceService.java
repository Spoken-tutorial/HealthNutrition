package com.health.service;

import java.util.List;

import com.health.model.TopicLanMapping;
import com.health.model.TrainingResource;

public interface TrainingResourceService {

    TrainingResource findByTrainingResourceId(int trainingResourceId);

    List<TrainingResource> findByTopicLanMapping(TopicLanMapping topicLanMapping);

    List<TrainingResource> findAll();

    void save(TrainingResource trainingResource);

    void saveAll(List<TrainingResource> trainingResourceList);

}
