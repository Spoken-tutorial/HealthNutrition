package com.health.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.TopicCategoryMapping;
import com.health.model.TrainingTopic;
import com.health.repository.TrainingTopicRepository;
import com.health.service.TrainingTopicService;

@Service
public class TrainingTopicServiceImpl implements TrainingTopicService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingTopicServiceImpl.class);

    @Autowired
    private TrainingTopicRepository trainingTopicRepo;

    @Override
    public int getNewId() {

        try {
            return trainingTopicRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Training Topic Service Impl: {}", trainingTopicRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public Set<TrainingTopic> findByTopicCat(List<TopicCategoryMapping> topics) {

        return trainingTopicRepo.findByTopicCat(topics);
    }

}
