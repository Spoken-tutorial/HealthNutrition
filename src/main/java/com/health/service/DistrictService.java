package com.health.service;

import java.util.List;
import java.util.Set;

import com.health.model.City;
import com.health.model.District;
import com.health.model.State;


/**
 * This interface has all the method declaration related to District object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface DistrictService {

	/**
	 * Persist District object into database
	 * @param dist District object
	 */
	void save(District dist);
	
	/**
	 * Find District object given id value
	 * @param id int value
	 * @return District object
	 */
	District findById(int id);
	
	/**
	 * Find all the District object from database
	 * @return list of District object
	 */
	List<District> findAll();
	
	 /**
     * Find list of District object given State object
     * @param state State object
     * @return list of District object
     */
	List<District> findAllByState(State state);
	
}
