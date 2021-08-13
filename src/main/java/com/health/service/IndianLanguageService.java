package com.health.service;

import java.util.List;

import com.health.model.IndianLanguage;

/**
 * This interface has all the method declaration related to IndianLanguage object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface IndianLanguageService {
	
	/**
	 * Find the next unique id for brochure object
	 * @return primitive integer value
	 */
	int getNewId();
	
	/**
	 * Find IndianLanguage object given name
	 * @param lanName String object
	 * @return IndianLanguage object
	 */
	IndianLanguage findByName(String lanName);
	
	/**
	 * Find all the IndianLanguage object from database
	 * @return list of IndianLanguage object
	 */
	List<IndianLanguage> findAll();

}
