package com.health.service;

import java.util.List;

import com.health.model.PostQuestionaire;
import com.health.model.User;
/**
 * This interface has all the method declaration related to PostQuestionaire object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface PostQuestionaireService {

	/**
	 * Find the next unique id for PostQuestionaire object
	 * @return primitive integer value
	 */
	int getNewCatId();

	/**
	 * Persist PostQuestionaire object into database
	 * @param temp PostQuestionaire object
	 */
	void save(PostQuestionaire temp);

	/**
	 * Find all the PostQuestionaire object from database
	 * @return list of PostQuestionaire object
	 */
	List<PostQuestionaire> findAll();

	/**
	 * Find list of PostQuestionaire object given user object
	 * @param user user object
	 * @return list of PostQuestionaire objects
	 */
	List<PostQuestionaire> findByUser(User user);
}
