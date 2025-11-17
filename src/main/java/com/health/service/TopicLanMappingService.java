package com.health.service;

import java.util.List;

import com.health.model.Language;
import com.health.model.Topic;
import com.health.model.TopicLanMapping;

public interface TopicLanMappingService {

    TopicLanMapping findBTopicLanId(int topicLanId);

    List<TopicLanMapping> findByLan(Language lan);

    List<TopicLanMapping> findByTopic(Topic topic);

    TopicLanMapping findByTopicAndLan(Topic topic, Language lan);

    List<TopicLanMapping> findAll();

    void save(TopicLanMapping topicLanMapping);

    void saveAll(List<TopicLanMapping> topicLanMappingList);

}
