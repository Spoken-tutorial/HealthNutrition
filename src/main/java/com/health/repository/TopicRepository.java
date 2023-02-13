package com.health.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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
	//@Cacheable(cacheNames = "topics")
	@Query("select max(topicId) from Topic")
	int getNewId();
	
	/**
	 * Find Topic object given topic name
	 * @param topicName String object
	 * @return Topic object
	 */
	//@Cacheable(cacheNames = "topics", key="#topicName")
	Topic findBytopicName(String topicName);
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	void deleteById(int id);
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	<S extends Topic> S save(S entity);
	
	//@Cacheable(cacheNames = "topics", key="#id")
	Optional<Topic> findById(int id);
	
	
			
}
