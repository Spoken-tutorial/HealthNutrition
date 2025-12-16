package com.health.service;

import java.util.List;

import com.health.model.TopicLanMapping;
import com.health.model.TrainingResource;

public interface TrainingResourceService {

    TrainingResource findByTrainingResourceId(int trainingResourceId);

    List<TrainingResource> findByTopicLanMapping(TopicLanMapping topicLanMapping);

    List<TrainingResource> findByTopicLanMappingInAndStatusTrue(List<TopicLanMapping> topicLanMappingList);

    List<TrainingResource> findAll();

    List<TrainingResource> findAllByStatusTrue();

    void save(TrainingResource trainingResource);

    void saveAll(List<TrainingResource> trainingResourceList);

}
