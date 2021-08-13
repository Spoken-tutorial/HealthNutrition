package com.health.service;

import java.util.List;
import java.util.Set;

import com.health.model.District;
import com.health.model.State;

/**
 * This interface has all the method declaration related to State object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface StateService {

	/**
	 * Persist State object into database
	 * @param state State object
	 */
	void save(State state);
	
	/**
	 * Find State object given Id
	 * @param id int value
	 * @return State object
	 */
	State findById(int id);
	
	/**
	 * Find all the state object from database
	 * @return list of State object
	 */
	List<State> findAll();
	
	/**
	 * This method add state to district 
	 * @param state State object
	 * @param districts list of district object
	 * @return State object
	 */
	State addStateToDistrict(State state,Set<District> districts);
}
