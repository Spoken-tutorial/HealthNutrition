package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.LogManegement;
import com.health.repository.LogMangementRepository;
import com.health.service.LogMangementService;

/**
 * Default implementation of the {@link com.health.service.LogMangementService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class LogMangementServiceImpl implements LogMangementService {
    private static final Logger logger = LoggerFactory.getLogger(LogMangementServiceImpl.class);

    @Autowired
    private LogMangementRepository logRepo;

    /**
     * @see com.health.service.LogMangementService#getNewId()
     */
    @Override
    public int getNewId() {
        // TODO Auto-generated method stub
        try {
            return logRepo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" New Id error in  LogManagement Service Impl: {}", logRepo.getNewId(), e);
            return 1;
        }
    }

    /**
     * @see com.health.service.LogMangementService#save(LogManegement)
     */
    @Override
    public void save(LogManegement log) {
        // TODO Auto-generated method stub
        logRepo.save(log);
    }

    @Override
    public List<LogManegement> getLogsWithSuperUser() {
        return logRepo.getLogsWithSuperUser();

    }

}
