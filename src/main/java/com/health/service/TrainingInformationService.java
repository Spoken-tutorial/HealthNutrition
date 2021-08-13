package com.health.service;

import java.util.List;
import java.util.Set;

import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;
import com.health.model.User;

/**
 * This interface has all the method declaration related to TrainingInformation object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TrainingInformationService {

	/**
	 * Find the next unique id for TrainingInformation object
	 * @return primitive integer value
	 */
	int getNewId();

	/**
	 * Persist TrainingInformation object into database
	 * @param temp TrainingInformation object
	 */
	void save(TrainingInformation temp);

	/**
	 * Add trainee to database
	 * @param training TrainingInformation object
	 * @param trainee TraineeInformation object
	 */
	void addTrainee(TrainingInformation training,Set<TraineeInformation> trainee);

	/**
	 * Find all the TrainingInformation object from database
	 * @return List of TrainingInformation objects
	 */
	List<TrainingInformation> findAll();

	/**
	 * Find TrainingInformation object given id
	 * @param id int
	 * @return TrainingInformation object
	 */
	TrainingInformation getById(int id);


	/**
	 * List of TrainingInformation object given user object
	 * @param user user object
	 * @return list of TrainingInformation object
	 */
	List<TrainingInformation> findByUser(User user);
}
