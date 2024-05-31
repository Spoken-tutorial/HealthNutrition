package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.FeedbackForm;
import com.health.repository.FeedbackContactFormRepository;
import com.health.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    private FeedbackContactFormRepository fRepo;

    @Override
    public int getNewId() {

        try {
            return fRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in FeedBack Service Impl: {}", fRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(FeedbackForm ff) {

        fRepo.save(ff);
    }

    @Override
    public List<FeedbackForm> findAll() {

        return (List<FeedbackForm>) fRepo.findAll();
    }

}
