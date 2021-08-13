package com.health.service;

import java.util.List;

import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;

/**
 * This interface has all the method declaration related to TraineeInformation object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TraineeInformationService {

	/**
	 * Find the next unique id for TraineeInformation object
	 * @return primitive integer value
	 */
	int getNewId();

	/**
	 * Find all the TrainingInformation object from database
	 * @return list of TrainingInformation object
	 */
	List<TraineeInformation> findAll();

	/**
	 * List of TraineeInformation object given TrainingInformation object
	 * @param trainingId TrainingInformation object
	 * @return List of TraineeInformation object
	 */
	List<TraineeInformation> findAllBytraineeInfos(TrainingInformation trainingId);

	/**
	 * Find TrainingInformation object given id
	 * @param traineeId int value
	 * @return TrainingInformation object
	 */
	TraineeInformation findById(int traineeId);

	/**
	 * Persist TraineeInformation object into database
	 * @param temp TraineeInformation object
	 */
	void save(TraineeInformation temp);




}
