package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.PostQuestionaire;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to PostQuestionaire object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface PostQuestionaireRepository extends CrudRepository<PostQuestionaire, Integer>{

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from PostQuestionaire")
	int getNewId();

	/**
	 * find list of PostQuestionaire given user object 
	 * @param usr
	 * @return list of PostQuestionaire object
	 */
	@Query("from PostQuestionaire where user=?1")
	List<PostQuestionaire> findByUser(User usr);

}
