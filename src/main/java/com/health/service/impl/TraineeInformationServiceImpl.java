package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;
import com.health.repository.TraineeInformationRepository;
import com.health.service.TraineeInformationService;

@Service
public class TraineeInformationServiceImpl implements TraineeInformationService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeInformationServiceImpl.class);

    @Autowired
    private TraineeInformationRepository repo;

    @Override
    public int getNewId() {

        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in Trainee Information Service Impl: {}", repo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public List<TraineeInformation> findAll() {

        return (List<TraineeInformation>) repo.findAll();
    }

    @Override
    public List<TraineeInformation> findAllBytraineeInfos(TrainingInformation trainingId) {

        return repo.findAllBytraineeInfos(trainingId);
    }

    @Override
    public TraineeInformation findById(int traineeId) {

        try {
            return repo.findById(traineeId).get();
        } catch (Exception e) {

            logger.error("Id error in Trainee Information Service Impl: {}", traineeId, e);
            return null;
        }
    }

    @Override
    public void save(TraineeInformation temp) {

        repo.save(temp);
    }

}
