package com.health.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.TrainingInformation;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to TrainingInformation object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TrainingInformationRespository extends CrudRepository<TrainingInformation,Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(trainingId) from TrainingInformation")
	int getNewId();

	/**
	 * List of TrainingInformation object given user object
	 * @param user user object
	 * @return list of TrainingInformation object
	 */
	@Query("from TrainingInformation where user=?1")
	List<TrainingInformation> findByUser(User user);
}
