package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.Language;
import com.health.model.Topic;
import com.health.model.TopicLanMapping;

public interface TopicLanMappingRepository extends JpaRepository<TopicLanMapping, Integer> {

    TopicLanMapping findByTopicLanId(int topicLanId);

    List<TopicLanMapping> findByLan(Language lan);

    List<TopicLanMapping> findByTopic(Topic topic);

    TopicLanMapping findByTopicAndLan(Topic topic, Language lan);

}
