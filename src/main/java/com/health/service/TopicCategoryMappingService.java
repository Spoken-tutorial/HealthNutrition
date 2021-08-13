package com.health.service;

import java.util.List;

import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;

/**
 * This interface has all the method declaration related to TopicCategoryMapping object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TopicCategoryMappingService {

	/**
	 * Persist TopicCategoryMapping object into database
	 * @param local TopicCategoryMapping object
	 */
	void save(TopicCategoryMapping local);
	
	/**
	 * Find the next unique id for TopicCategoryMapping object
	 * @return primitive integer value
	 */
	int getNewId();
	
	/**
	 * Find list of TopicCategoryMapping object given category object
	 * @param cat category object
	 * @return list of TopicCategoryMapping object
	 */
	List<TopicCategoryMapping> findAllByCategory(Category cat);
	
	/**
	 * Find list of TopicCategoryMapping object given Topic object
	 * @param topic Topic object
	 * @return list of TopicCategoryMapping object
	 */
	List<TopicCategoryMapping> findAllByTopic(Topic topic);
	
	/**
	 * Find TopicCategoryMapping object given List of UserRole Object
	 * @param userRoles list of UserRole object
	 * @return list of TopicCategoryMapping object
	 */
	List<TopicCategoryMapping> findAllByCategoryBasedOnUserRoles(List<UserRole> userRoles);
	
	/**
	 * Find TopicCategoryMapping object given category object and Topic object
	 * @param cat category object
	 * @param topic Topic object
	 * @return TopicCategoryMapping object
	 */
	TopicCategoryMapping findAllByCategoryAndTopic(Category cat,Topic topic);
	
	/**
	 * Find TopicCategoryMapping object given category object and order value
	 * @param cat category object
	 * @param order int value
	 * @return TopicCategoryMapping object
	 */
	TopicCategoryMapping findByCategoryAndOrder(Category cat, int order);
}

