package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.FeedbackMasterTrainer;
import com.health.model.User;
import com.health.repository.FeedBackRepository;
import com.health.service.FeedBackMasterTrainerService;

@Service
public class FeedBackMasterTrainerServiceImpl implements FeedBackMasterTrainerService {
    private static final Logger logger = LoggerFactory.getLogger(FeedBackMasterTrainerServiceImpl.class);

    @Autowired
    private FeedBackRepository feedRepo;

    @Override
    public int getNewId() {

        try {
            return feedRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in FeedBackMasterTrainerServiceImpl: {}", feedRepo.getNewId(), e);
            return 1;

        }
    }

    @Override
    public void save(FeedbackMasterTrainer temp) {

        feedRepo.save(temp);
    }

    @Override
    public List<FeedbackMasterTrainer> findByUser(User user) {

        return feedRepo.findByUser(user);
    }

}
