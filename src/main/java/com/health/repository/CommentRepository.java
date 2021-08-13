package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Comment;
import com.health.model.Tutorial;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Comment object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CommentRepository extends CrudRepository<Comment, Integer>{


	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(commentId) from Comment")
	int getNewId();

	/**
	 * find list of comment object
	 * @param type component name of tutorial object
	 * @param usr user object
	 * @param tut tutorial object
	 * @param roleName role object
	 * @return list of comment object
	 */
	@Query("from Comment where type=?1 and user=?2 and tutorialInfos=?3 and roleName=?4")
	List<Comment> getCommentBasedOnUserTutorialType(String type, User usr, Tutorial tut,String roleName);
	
	/**
	 * find list of comment
	 * @param type component name of tutorial object
	 * @param tut tutorial object
	 * @return list of comment object
	 */
	@Query("from Comment where type=?1 and tutorialInfos=?2")
	List<Comment> getCommentBasedOnTutorialType(String type, Tutorial tut);
}
