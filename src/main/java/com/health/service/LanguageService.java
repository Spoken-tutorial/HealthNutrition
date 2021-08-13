package com.health.service;

import java.util.List;

import com.health.model.Language;

/**
 * This interface has all the method declaration related to Language object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface LanguageService {
	
	/**
	 * Find all the language object from the database
	 * @return list of Language object
	 */
	List<Language> getAllLanguages();
	

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	int getnewLanId();
	
	/**
	 * Find Language object given lang name
	 * @param langName String value
	 * @return Langauge object
	 */
	Language getByLanName(String langName);
	
	/**
	 * Find Language object given lan id
	 * @param lanId int value
	 * @return Language object
	 */
	Language getById(int lanId);
	
	/**
	 * Persist Language object into database
	 * @param lan Language object
	 */
	void save(Language lan);

}
