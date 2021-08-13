package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Category;
import com.health.model.Language;
import com.health.model.Question;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;
import com.health.model.Tutorial;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Question object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface QuestionRepository extends CrudRepository<Question, Integer> {
	
	  
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
     @Query("select max(questionId) from Question")
	 int getNewId();
	
     /**
      * Find Question object given TopicCategoryMapping and Language object
      * @param topicCat TopicCategoryMapping object
      * @param lan Language object
      * @return Question object
      */
	 @Query("from Question where topicCatId = ?1 and lan = ?2")
	 Question findByTopicLan(TopicCategoryMapping topicCat, Language lan);
	 
	

}
