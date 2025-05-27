package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.TopicLanMapping;
import com.health.model.TrainingResource;

public interface TrainingResourceRepository extends JpaRepository<TrainingResource, Integer> {

    TrainingResource findByTrainingResourceId(int trainingResourceId);

    List<TrainingResource> findByTopicLanMapping(TopicLanMapping topicLanMapping);

}
