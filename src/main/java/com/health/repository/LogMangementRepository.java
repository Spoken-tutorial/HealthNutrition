package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.LogManegement;

/**
 * This Interface Extend CrudRepository to handle all database operation related to LogManegement object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface LogMangementRepository extends CrudRepository<LogManegement, Integer>{
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(logId) from LogManegement")
	int getNewId();
}

