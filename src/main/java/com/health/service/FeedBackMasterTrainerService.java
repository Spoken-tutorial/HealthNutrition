package com.health.service;

import java.util.List;

import com.health.model.FeedbackMasterTrainer;
import com.health.model.User;

public interface FeedBackMasterTrainerService {

    int getNewId();

    void save(FeedbackMasterTrainer temp);

    List<FeedbackMasterTrainer> findByUser(User user);

}