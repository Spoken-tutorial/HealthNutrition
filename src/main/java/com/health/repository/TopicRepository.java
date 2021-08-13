package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Category;
import com.health.model.Topic;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Topic object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TopicRepository extends CrudRepository<Topic , Integer>{
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(topicId) from Topic")
	int getNewId();
	
	/**
	 * Find Topic object given topic name
	 * @param topicName String object
	 * @return Topic object
	 */
	Topic findBytopicName(String topicName);
			
}
