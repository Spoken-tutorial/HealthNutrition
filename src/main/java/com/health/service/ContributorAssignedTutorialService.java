package com.health.service;

import java.util.List;

import com.health.domain.security.UserRole;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Language;
import com.health.model.TopicCategoryMapping;
import com.health.model.User;

/**
 * This interface has all the method declaration related to ContributorAssignedTutorial object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface ContributorAssignedTutorialService {
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	int getNewId();
	
	/**
	 * Persist ContributorAssignedTutorial object into database
	 * @param con ContributorAssignedTutorial object
	 */
	void save(ContributorAssignedTutorial con);
	
	/**
	 * Find all the ContributorAssignedTutorial object from the database
	 * @return list of ContributorAssignedTutorial object
	 */
	List<ContributorAssignedTutorial> findAll();
	
	/**
	 * Find list of ContributorAssignedTutorial object given list of TopicCategoryMapping object
	 * @param topCat list of TopicCategoryMapping object
	 * @return list of ContributorAssignedTutorial object
	 */
	List<ContributorAssignedTutorial> findAllByTopicCat(List<TopicCategoryMapping> topCat);
	
	/**
	 * Find list of ContributorAssignedTutorial object given TopicCategoryMapping object
	 * @param topCat TopicCategoryMapping object
	 * @return list of ContributorAssignedTutorial object
	 */
	List<ContributorAssignedTutorial> findByTopicCat(TopicCategoryMapping topCat);
	
	/**
	 * Find list of ContributorAssignedTutorial object given Language object
	 * @param lan Language object
	 * @return list of ContributorAssignedTutorial object
	 */
	List<ContributorAssignedTutorial> findAllByLan(Language lan);
	
	/**
	 * Find list of ContributorAssignedTutorial object given list of TopicCategoryMapping object and list of UserRole object
	 * @param topCat list of TopicCategoryMapping object
	 * @param usrRole list of UserRole object
	 * @return list of ContributorAssignedTutorial object
	 */
	List<ContributorAssignedTutorial> findByTopicCatLan(List<TopicCategoryMapping> topCat, List<UserRole> usrRole);
	
	/**
	 * Find ContributorAssignedTutorial object given list of TopicCategoryMapping object and Language object
	 * @param topCat list of TopicCategoryMapping object
	 * @param lan language object
	 * @return list of ContributorAssignedTutorial object
	 */
	List<ContributorAssignedTutorial> findAllByTopicCatAndLanViewPart(List<TopicCategoryMapping> topCat,Language lan);
	/**
	 * Find ContributorAssignedTutorial object given TopicCategoryMapping and Language object
	 * @param topCat TopicCategoryMapping object
	 * @param lan Language object
	 * @return ContributorAssignedTutorial object
	 */
	ContributorAssignedTutorial findByTopicCatAndLanViewPart(TopicCategoryMapping topCat,Language lan);
	
	/**
	 * Find ContributorAssignedTutorial object given id value
	 * @param id int value
	 * @return ContributorAssignedTutorial object
	 */
	ContributorAssignedTutorial findById(int id);
}
