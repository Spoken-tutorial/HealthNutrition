package com.health.service;

import java.util.List;

import com.health.model.FeedbackForm;

public interface FeedbackService {

    int getNewId();

    void save(FeedbackForm ff);

    List<FeedbackForm> findAll();
}
