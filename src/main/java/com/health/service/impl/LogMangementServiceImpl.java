package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.LogManegement;
import com.health.repository.LogMangementRepository;
import com.health.service.LogMangementService;

@Service
public class LogMangementServiceImpl implements LogMangementService {
    private static final Logger logger = LoggerFactory.getLogger(LogMangementServiceImpl.class);

    @Autowired
    private LogMangementRepository logRepo;

    @Override
    public int getNewId() {

        try {
            return logRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in  LogManagement Service Impl: {}", logRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(LogManegement log) {

        logRepo.save(log);
    }

    @Override
    public List<LogManegement> getLogsWithSuperUser() {
        return logRepo.getLogsWithSuperUser();

    }

}
