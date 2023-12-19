package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.FeedbackForm;
import com.health.repository.FeedbackContactFormRepository;
import com.health.service.FeedbackService;

/**
 * Default implementation of the {@link com.health.service.FeedbackService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    private FeedbackContactFormRepository fRepo;

    /**
     * @see com.health.service.FeedbackService#getNewId()
     */

    @Override
    public int getNewId() {
        // TODO Auto-generated method stub
        try {
            return fRepo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" New Id error in FeedBack Service Impl: {}", fRepo.getNewId(), e);
            return 1;
        }
    }

    /**
     * @see com.health.service.FeedbackService#getNewId()
     */
    @Override
    public void save(FeedbackForm ff) {
        // TODO Auto-generated method stub

        fRepo.save(ff);
    }

    /**
     * @see com.health.service.FeedbackService#findAll()
     */
    @Override
    public List<FeedbackForm> findAll() {
        // TODO Auto-generated method stub
        return (List<FeedbackForm>) fRepo.findAll();
    }

}
