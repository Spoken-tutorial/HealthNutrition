package com.health.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;

/**
 * This Interface Extend CrudRepository to handle all database operation related to TraineeInformation object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TraineeInformationRepository extends CrudRepository<TraineeInformation,Integer>{
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(TraineeId) from TraineeInformation")
	int getNewId();
	
	/**
	 * List of TraineeInformation object given TrainingInformation object
	 * @param trainingId TrainingInformation object
	 * @return List of TraineeInformation object
	 */
	List<TraineeInformation> findAllBytraineeInfos(TrainingInformation trainingId);
}