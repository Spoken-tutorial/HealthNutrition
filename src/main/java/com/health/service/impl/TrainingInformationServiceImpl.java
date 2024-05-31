package com.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;
import com.health.model.User;
import com.health.repository.TrainingInformationRespository;
import com.health.service.TrainingInformationService;

@Service
public class TrainingInformationServiceImpl implements TrainingInformationService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingInformationServiceImpl.class);

    @Autowired
    private TrainingInformationRespository trainingInfoRepo;

    @Override
    public int getNewId() {

        try {
            return trainingInfoRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Training Information Service Impl: {}", trainingInfoRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(TrainingInformation temp) {

        trainingInfoRepo.save(temp);

    }

    @Override
    public void addTrainee(TrainingInformation training, Set<TraineeInformation> trainee) {

        training.getTraineeInfos().addAll(trainee);
        trainingInfoRepo.save(training);
    }

    @Override
    public List<TrainingInformation> findAll() {

        return (List<TrainingInformation>) trainingInfoRepo.findAll();
    }

    @Override
    public TrainingInformation getById(int id) {

        try {
            Optional<TrainingInformation> local = trainingInfoRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error("Id error in Training Information Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public List<TrainingInformation> findByUser(User user) {

        return trainingInfoRepo.findByUser(user);
    }

}