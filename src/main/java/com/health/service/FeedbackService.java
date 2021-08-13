package com.health.service;

import java.util.List;

import com.health.model.FeedbackForm;

/**
 * This interface has all the method declaration related to FeedbackForm object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface FeedbackService {

	/**
	 * Find the next unique id for brochure object
	 * @return primitive integer value
	 */
	int getNewId();
	
	/**
	 * Persist FeedbackForm object into database
	 * @param ff FeedbackForm object
	 */
	void save(FeedbackForm ff);
	
	/**
	 * Find all FeedbackForm object from database
	 * @return list of FeedbackForm object
	 */
	List<FeedbackForm> findAll();
}
