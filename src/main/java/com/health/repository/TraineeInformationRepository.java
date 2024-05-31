package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;

public interface TraineeInformationRepository extends CrudRepository<TraineeInformation, Integer> {

    @Query("select max(TraineeId) from TraineeInformation")
    int getNewId();

    List<TraineeInformation> findAllBytraineeInfos(TrainingInformation trainingId);

}