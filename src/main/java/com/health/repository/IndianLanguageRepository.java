package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.IndianLanguage;

/**
 * This Interface Extend CrudRepository to handle all database operation related to IndianLangauge object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface IndianLanguageRepository extends CrudRepository<IndianLanguage, Integer>{
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from IndianLanguage")
	int getNewId();
	
	/**
	 * Find IndianLangauge object given name
	 * @param lanName String object
	 * @return IndianLanguage object
	 */
	IndianLanguage findBylanName(String lanName);

}
