package com.health.service;

import java.util.List;

import com.health.model.Language;
import com.health.model.Question;
import com.health.model.TopicCategoryMapping;
/**
 * This interface has all the method declaration related to Brochure object Question operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface QuestionService {

	/**
	 * Find the next unique id for Question object
	 * @return primitive integer value
	 */
	int getNewId();
	
	/**
	 * Persist Question object into database
	 * @param ques Question object
	 */
	void save(Question ques);
	
	/**
	 * Find Question object given id
	 * @param id int
	 * @return  Question object
	 */
	Question findById(int id);
	
	/**
	 * Find Question object given TopicCategoryMapping and Language object
	 * @param topicCat TopicCategoryMapping object
	 * @param lan Language object
	 * @return Question object
	 */
	Question getQuestionBasedOnTopicCatAndLan(TopicCategoryMapping topicCat, Language lan);
	
	/**
	 * Find all the Question object from database
	 * @return list of Question object
	 */
	List<Question> findAll();
}
