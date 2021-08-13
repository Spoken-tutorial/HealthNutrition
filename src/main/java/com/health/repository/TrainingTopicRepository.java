package com.health.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.TopicCategoryMapping;
import com.health.model.TrainingTopic;

/**
 * This Interface Extend CrudRepository to handle all database operation related to TrainingTopic object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TrainingTopicRepository extends CrudRepository<TrainingTopic, Integer>{

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(trainingTopicId) from TrainingTopic")
	int getNewId();
	
	/**
	 * List of TrainingTopic object given list of TopicCategoryMapping object
	 * @param topCat list of TopicCategoryMapping object
	 * @return list of TrainingTopic object
	 */
	@Query("from TrainingTopic where topicCatId IN (:TopicCat)")
	Set<TrainingTopic> findByTopicCat(@Param("TopicCat")List<TopicCategoryMapping> topCat);
}
