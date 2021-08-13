package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to ContributorAssignedMultiUserTutorial object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface ContributorAssignedMultiUserTutorialRepository extends CrudRepository<ContributorAssignedMultiUserTutorial,Integer > {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from ContributorAssignedMultiUserTutorial")
	int getNewId();
	
	/**
	 * Find list of ContributorAssignedMultiUserTutorial object given user object
	 * @param user user object
	 * @return list of ContributorAssignedMultiUserTutorial object
	 */
	List<ContributorAssignedMultiUserTutorial> findAllByuser(User user);
}
