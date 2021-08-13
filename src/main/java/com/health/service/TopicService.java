package com.health.service;

import java.util.List;

import com.health.model.Topic;

/**
 * This interface has all the method declaration related to Topic object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TopicService {

	/**
	 * Find the next unique id for Topic object
	 * @return primitive integer value
	 */
	int getNewTopicId();
	
	/**
	 * Find Topic object given topic name
	 * @param topic String object
	 * @return Topic object
	 */
	Topic findBytopicName(String topic);
	
	/**
	 * Find Topic object given Id
	 * @param id int
	 * @return Topic object
	 */
	Topic findById(int id);
	
	/**
	 * Find all the Topic object from Database
	 * @return list of Topic Object
	 */
	List<Topic> findAll();
	
	/**
	 * Persist Topic object into database
	 * @param topic Topic object
	 */
	void save(Topic topic);
}
