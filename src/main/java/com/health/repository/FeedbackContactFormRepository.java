package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.FeedbackForm;

/**
 * This Interface Extend CrudRepository to handle all database operation related to FeedbackContactForm object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface FeedbackContactFormRepository extends CrudRepository<FeedbackForm, Integer>{
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from FeedbackForm")
	int getNewId();

}
