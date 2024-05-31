package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.IndianLanguage;
import com.health.repository.IndianLanguageRepository;
import com.health.service.IndianLanguageService;

@Service
public class IndianLanguageServiceImpl implements IndianLanguageService {
    private static final Logger logger = LoggerFactory.getLogger(IndianLanguageServiceImpl.class);

    @Autowired
    private IndianLanguageRepository repo;

    @Override
    public int getNewId() {

        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in Indian Language  Service Impl: {}", repo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public IndianLanguage findByName(String lanName) {

        try {
            return repo.findBylanName(lanName);
        } catch (Exception e) {

            logger.error(" Name error in Indian Language  Service Impl: {}", lanName, e);
            return null;
        }
    }

    @Override
    public List<IndianLanguage> findAll() {

        return (List<IndianLanguage>) repo.findAll();
    }
}
