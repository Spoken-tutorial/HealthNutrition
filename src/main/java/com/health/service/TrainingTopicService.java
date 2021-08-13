package com.health.service;

import java.util.List;
import java.util.Set;

import com.health.model.TopicCategoryMapping;
import com.health.model.TrainingTopic;

/**
 * This interface has all the method declaration related to TrainingTopic object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TrainingTopicService {

	/**
	 * Find the next unique id for TrainingTopic object
	 * @return primitive integer value
	 */
	int getNewId();
	
	/**
	 * Find List of TrainingTopic object given List of TopicCategoryMapping object
	 * @param topics list of TopicCategoryMapping object
	 * @return list of TopicCategoryMapping objects
	 */
	Set<TrainingTopic> findByTopicCat(List<TopicCategoryMapping> topics);
}
