package com.health.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.health.model.Language;

/**
 * This Interface Extend JpaRepository to handle all database operation related to Language object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface LangaugeRepository extends JpaRepository<Language,Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(lanId) from Language")
	int getNewId();
	
	/**
	 * Find Language object given lang name
	 * @param langName String value
	 * @return Langauge object
	 */
	Language findBylangName(String langName);

}
