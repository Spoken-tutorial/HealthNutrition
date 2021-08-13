package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.FeedbackMasterTrainer;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Feedback object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface FeedBackRepository extends CrudRepository<FeedbackMasterTrainer, Integer>{

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(TrainerFeedId) from FeedbackMasterTrainer")
	int getNewId();

	/**
	 * Find list of feedbackMasterTrainer object given user object
	 * @param usr user object
	 * @return list of FeedbackMasterTrainer object
	 */
	@Query("from FeedbackMasterTrainer where user=?1")
	List<FeedbackMasterTrainer> findByUser(User usr);

}
