package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to User object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {
	
	/**
	 * Find User object given username
	 * @param username String object
	 * @return user object
	 */
	User findByUsername(String username);
	
	/**
	 * Find User object given email
	 * @param email String object
	 * @return user object
	 */
	User findByEmail(String email);
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from User")
	long getNewId();
	
	/**
	 * Find User object given Token
	 * @param token String object
	 * @return user object
	 */
	User findBytoken(String token);
	

	 
}
