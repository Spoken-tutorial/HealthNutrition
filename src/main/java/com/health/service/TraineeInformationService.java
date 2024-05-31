package com.health.service;

import java.util.List;

import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;

public interface TraineeInformationService {

    int getNewId();

    List<TraineeInformation> findAll();

    List<TraineeInformation> findAllBytraineeInfos(TrainingInformation trainingId);

    TraineeInformation findById(int traineeId);

    void save(TraineeInformation temp);

}
