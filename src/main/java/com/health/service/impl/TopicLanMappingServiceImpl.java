package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Language;
import com.health.model.Topic;
import com.health.model.TopicLanMapping;
import com.health.repository.TopicLanMappingRepository;
import com.health.service.TopicLanMappingService;

@Service
public class TopicLanMappingServiceImpl implements TopicLanMappingService {

    private static final Logger logger = LoggerFactory.getLogger(TopicLanMappingServiceImpl.class);

    @Autowired
    private TopicLanMappingRepository repo;

    @Override
    public TopicLanMapping findBTopicLanId(int topicLanId) {

        return repo.findByTopicLanId(topicLanId);
    }

    @Override
    public List<TopicLanMapping> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(TopicLanMapping topicLanMapping) {
        repo.save(topicLanMapping);

    }

    @Override
    public void saveAll(List<TopicLanMapping> topicLanMappingList) {
        repo.saveAll(topicLanMappingList);

    }

    @Override
    public List<TopicLanMapping> findByLan(Language lan) {

        return repo.findByLan(lan);
    }

    @Override
    public List<TopicLanMapping> findByTopic(Topic topic) {

        return repo.findByTopic(topic);
    }

    @Override
    public TopicLanMapping findByTopicAndLan(Topic topic, Language lan) {

        return repo.findByTopicAndLan(topic, lan);
    }

}
