package com.health.service;

import java.util.List;

import com.health.model.FeedbackMasterTrainer;
import com.health.model.User;
/**
 * This interface has all the method declaration related to FeedbackMasterTrainer object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface FeedBackMasterTrainerService {


	/**
	 * Find the next unique id for FeedbackMasterTrainer object
	 * @return primitive integer value
	 */
	int getNewId();

	/**
	 * Persist FeedbackMasterTrainer object into database
	 * @param temp FeedbackMasterTrainer object
	 */
	void save(FeedbackMasterTrainer temp);

	/**
	 * Find list of FeedbackMasterTrainer given Usr object
	 * @param user user object
	 * @return FeedbackMasterTrainer object
	 */
	List<FeedbackMasterTrainer> findByUser(User user);

}