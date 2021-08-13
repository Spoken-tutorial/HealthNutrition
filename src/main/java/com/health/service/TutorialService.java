package com.health.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.Tutorial;

/**
 * This interface has all the method declaration related to Tutorial object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TutorialService {

	
	/**
	 * List out all the tutorial given list of ContributorAssignedTutorial object under pagination concept
	 * @param con list of ContributorAssignedTutorial object
	 * @param page Pageable object
	 * @return page object
	 */
	Page<Tutorial> findAllByconAssignedTutorialListPagination(List<ContributorAssignedTutorial> con,Pageable page);
	
	
	/**
	 * List out all the tutorial given ContributorAssignedTutorial object under paginaytion concept
	 * @param con ContributorAssignedTutorial object
	 * @param page Pageable object
	 * @return page object
	 */
	Page<Tutorial> findAllByconAssignedTutorialPagination(ContributorAssignedTutorial con,Pageable page);
	
	/**
	 * List out all the tutorial based on pagination
	 * @param page Pageable object
	 * @return Page object
	 */
	Page<Tutorial> findAllPagination(Pageable page);
	
	/**
	 * List out all the tutorial object from database
	 * @return list of Tutorial object
	 */
	List<Tutorial> findAll();

	/**
	 * Find the next unique id for Tutorial object
	 * @return primitive integer value
	 */
	int getNewId();

	/**
	 * List of Tutorial Object given ContributorAssignedTutorial object
	 * @param con ContributorAssignedTutorial object
	 * @return list of Tutorial object
	 */
	List<Tutorial> findAllByContributorAssignedTutorial(ContributorAssignedTutorial con);

	/**
	 * List of Tutorial Object given list of ContributorAssignedTutorial object
	 * @param con list of ContributorAssignedTutorial object
	 * @return list of Tutorial object
	 */
	List<Tutorial> findAllByContributorAssignedTutorialList(List<ContributorAssignedTutorial> con);

	/**
	 * Persist Tutorial object into database
	 * @param tut Tutorial object
	 */
	void save(Tutorial tut);

	/**
	 * Find Tutorial object given id
	 * @param id int value
	 * @return Tutorial object
	 */
	Tutorial getById(int id);
	
	/**
	 * List of Tutorial Object given status value
	 * @param status boolean value
	 * @return list of Tutorial object
	 */
	List<Tutorial> findAllBystatus(boolean status);

}