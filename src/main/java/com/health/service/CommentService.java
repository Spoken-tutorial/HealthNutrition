package com.health.service;

import java.util.List;

import com.health.model.Comment;
import com.health.model.Tutorial;
import com.health.model.User;
/**
 * This interface has all the method declaration related to Comment object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CommentService {

	/**
	 * Find the next unique id for brochure object
	 * @return primitive integer value
	 */
	int getNewCommendId();
	
	/**
	 * Persist Comment object into database
	 * @param com Comment object
	 */
	void save(Comment com);
	
	/**
	 * find list of comment object
	 * @param type component name of tutorial object
	 * @param usr user object
	 * @param tut tutorial object
	 * @param role role object
	 * @return list of comment object
	 */
	List<Comment> getCommentBasedOnUserTutorialType(String type, User usr, Tutorial tut,String role);
	
	
	/**
	 * find list of comment
	 * @param type component name of tutorial object
	 * @param tut tutorial object
	 * @return list of comment object
	 */
	List<Comment> getCommentBasedOnTutorialType(String type, Tutorial tut);
}
