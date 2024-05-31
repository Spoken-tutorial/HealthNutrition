package com.health.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Category;
import com.health.model.Topic;
import com.health.model.Tutorial;
import com.health.repository.TopicRepository;
import com.health.repository.TutorialRepository;
import com.health.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
    private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    @Autowired
    private TopicRepository topicRepo;

    @Autowired
    private TutorialRepository tutRepo;

    @Override
    public int getNewTopicId() {

        try {
            return topicRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in Topic Service Impl: {}", topicRepo.getNewId(), e);
            return 1;
        }
    }

    @Override

    public Topic findBytopicName(String name) {

        return topicRepo.findBytopicName(name);
    }

    @Override

    public Topic findById(int id) {

        try {
            Optional<Topic> local = topicRepo.findById(id);

            return local.get();
        } catch (Exception e) {

            logger.error("Id error in Topic Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public List<Topic> findAll() {

        List<Topic> topics = (List<Topic>) topicRepo.findAll();
        Collections.sort(topics);
        return topics;
    }

    @Override
    @CachePut(cacheNames = "topics", key = "#topic.id")
    public void save(Topic topic) {

        topicRepo.save(topic);
    }

    @Override
    @Cacheable(cacheNames = "topics")
    public List<Topic> getTopicsForcache() {

        List<Tutorial> tutorials = tutRepo.findAllByStatus(true);

        Set<Topic> topicTemp = new HashSet<Topic>();
        for (Tutorial temp : tutorials) {

            Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
            if (c.isStatus()) {
                topicTemp.add(temp.getConAssignedTutorial().getTopicCatId().getTopic());
            }
        }
        List<Topic> topicTempSorted = new ArrayList<Topic>(topicTemp);
        Collections.sort(topicTempSorted);

        return topicTempSorted;
    }
}
