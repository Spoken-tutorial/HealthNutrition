package com.health.service;

import com.health.model.LogManegement;

/**
 * This interface has all the method declaration related to LogManegement object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface LogMangementService {

	/**
	 * Find the next unique id for LogManegement object
	 * @return primitive integer value
	 */
	int getNewId();
	
	/**
	 * Persist LogManegement object into database
	 * @param log LogManegement object
	 */
	void save(LogManegement log);
}
