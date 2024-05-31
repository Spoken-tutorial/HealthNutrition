package com.health.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.repository.UserIndianLanguageMappingRepository;
import com.health.service.UserIndianLanguageMappingService;

@Service
public class UserIndianLanguageMappingimpl implements UserIndianLanguageMappingService {

    private static final Logger logger = LoggerFactory.getLogger(UserIndianLanguageMappingimpl.class);

    @Autowired
    private UserIndianLanguageMappingRepository repo;

    @Override
    public int getNewId() {

        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in User Indian Language Mapping Service Impl: {}", repo.getNewId(), e);
            return 1;
        }
    }

}
