package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.IndianLanguage;
import com.health.repository.IndianLanguageRepository;
import com.health.service.IndianLanguageService;

/**
 * Default implementation of the
 * {@link com.health.service.IndianLanguageService} interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class IndianLanguageServiceImpl implements IndianLanguageService {
    private static final Logger logger = LoggerFactory.getLogger(IndianLanguageServiceImpl.class);

    @Autowired
    private IndianLanguageRepository repo;

    /**
     * @see com.health.service.IndianLanguageService#getNewId()
     */
    @Override
    public int getNewId() {
        // TODO Auto-generated method stub
        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" New Id error in Indian Language  Service Impl: {}", repo.getNewId(), e);
            return 1;
        }
    }

    /**
     * @see com.health.service.IndianLanguageService#findByName(String)
     */
    @Override
    public IndianLanguage findByName(String lanName) {
        // TODO Auto-generated method stub
        try {
            return repo.findBylanName(lanName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" Name error in Indian Language  Service Impl: {}", lanName, e);
            return null;
        }
    }

    /**
     * @see com.health.service.IndianLanguageService#findAll()
     */
    @Override
    public List<IndianLanguage> findAll() {
        // TODO Auto-generated method stub
        return (List<IndianLanguage>) repo.findAll();
    }
}
