package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.TrainingInformation;
import com.health.model.User;

public interface TrainingInformationRespository extends CrudRepository<TrainingInformation, Integer> {

    @Query("select max(trainingId) from TrainingInformation")
    int getNewId();

    @Query("from TrainingInformation where user=?1")
    List<TrainingInformation> findByUser(User user);

}
