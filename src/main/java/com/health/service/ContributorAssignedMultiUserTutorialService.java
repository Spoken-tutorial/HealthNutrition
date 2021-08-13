package com.health.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.User;

/**
 * This interface has all the method declaration related to ContributorAssignedMultiUserTutorial object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface ContributorAssignedMultiUserTutorialService {

	/**
	 * Persist ContributorAssignedMultiUserTutorial object into database
	 * @param con ContributorAssignedMultiUserTutorial object
	 */
	void save(ContributorAssignedMultiUserTutorial con);
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	int getNewId();
	
	/**
	 * Find list of ContributorAssignedMultiUserTutorial object given user object
	 * @param usr user object
	 * @return list of ContributorAssignedMultiUserTutorial object
	 */
	List<ContributorAssignedMultiUserTutorial> getAllByuser(User usr);
}
