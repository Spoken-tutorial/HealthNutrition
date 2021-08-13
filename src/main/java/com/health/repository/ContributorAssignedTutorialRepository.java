package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Language;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to ContributorAssignedTutorial object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface ContributorAssignedTutorialRepository extends CrudRepository<ContributorAssignedTutorial, Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from ContributorAssignedTutorial")
	int getNewId();
	
	/**
	 * Find list of ContributorAssignedTutorial object given TopicCategoryMapping object
	 * @param topCat TopicCategoryMapping object
	 * @return list of ContributorAssignedTutorial object
	 */
	List<ContributorAssignedTutorial> findAllBytopicCatId(TopicCategoryMapping topCat);
	
	/**
	 * Find list of ContributorAssignedTutorial object given Language object
	 * @param lan Language object
	 * @return list of ContributorAssignedTutorial object
	 */
	List<ContributorAssignedTutorial> findAllBylan(Language lan);
	
	/**
	 * Find list of ContributorAssignedTutorial object given TopicCategoryMapping object and Language object
	 * @param topicCat TopicCategoryMapping object
	 * @param lan Language object
	 * @return list of ContributorAssignedTutorial object
	 */
	@Query("from ContributorAssignedTutorial where topicCatId=?1 and lan=?2")
	ContributorAssignedTutorial findByTopicCatLan(TopicCategoryMapping topicCat,Language lan);
	
	/**
	 * Find list of ContributorAssignedTutorial object given list of TopicCategoryMapping object
	 * @param topCat TopicCategoryMapping object
	 * @return list of ContributorAssignedTutorial object
	 */
	@Query("from ContributorAssignedTutorial where topicCatId IN (:TopicCat)")
	List<ContributorAssignedTutorial> findByTopicCat(@Param("TopicCat")List<TopicCategoryMapping> topCat);
}

