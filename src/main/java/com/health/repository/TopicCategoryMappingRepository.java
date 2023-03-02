package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Category;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;

/**
 * This Interface Extend CrudRepository to handle all database operation related to TopicCategoryMapping object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TopicCategoryMappingRepository extends CrudRepository<TopicCategoryMapping, Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(topicCategoryId) from TopicCategoryMapping")
	int getNewId();
	
	/**
	 * Find list of TopicCategoryMapping object given category object
	 * @param cat category object
	 * @return list of TopicCategoryMapping object
	 */
	@Query("from TopicCategoryMapping where cat=?1 order by order")
	List<TopicCategoryMapping> findAllBycat(Category cat);
	
	

	
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	void deleteById(int id);
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	<S extends TopicCategoryMapping> S save(S entity);
	
	/**
	 * Find list of TopicCategoryMapping object given Topic object
	 * @param topic Topic object
	 * @return list of TopicCategoryMapping object
	 */
	List<TopicCategoryMapping> findAllBytopic(Topic topic);
	
	/**
	 * Find TopicCategoryMapping object given category object and Topic object
	 * @param cat category object
	 * @param topic Topic object
	 * @return TopicCategoryMapping object
	 */
	@Query("from TopicCategoryMapping where cat=?1 and topic=?2")
	TopicCategoryMapping findBycatAndtopic(Category cat,Topic topic); 
	
	
	/**
	 * Find TopicCategoryMapping object given category object and order value
	 * @param cat category object
	 * @param order int value
	 * @return TopicCategoryMapping object
	 */
	@Query("from TopicCategoryMapping where cat=?1 and order=?2")
	TopicCategoryMapping findBycatAndorder(Category cat,int order); 
	
	
}