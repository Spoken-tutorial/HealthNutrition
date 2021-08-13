package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.UserIndianLanguageMapping;

/**
 * This Interface Extend CrudRepository to handle all database operation related to UserIndianLanguageMapping object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface UserIndianLanguageMappingRepository extends CrudRepository<UserIndianLanguageMapping,Integer>{

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from UserIndianLanguageMapping")
	int getNewId();
}
