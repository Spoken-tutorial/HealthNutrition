package com.health.repository;

import org.springframework.data.repository.CrudRepository;

import com.health.model.State;

/**
 * This Interface Extend CrudRepository to handle all database operation related to State object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface StateRepository extends CrudRepository<State, Integer>{
		
	/**
	 * Find State object given State name
	 * @param name String object
	 * @return State object
	 */
	State findBystateName(String name);
				
		
}
