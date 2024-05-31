package com.health.service;

import java.util.List;
import java.util.Set;

import com.health.model.TopicCategoryMapping;
import com.health.model.TrainingTopic;

public interface TrainingTopicService {

    int getNewId();

    Set<TrainingTopic> findByTopicCat(List<TopicCategoryMapping> topics);

}
