package com.health.service;

import java.util.List;

import com.health.model.Brochure;
import com.health.model.Category;
/**
 * This interface has all the method declaration related to Brochure object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface BrochureService {

	/**
	 * Find the next unique id for brochure object
	 * @return primitive integer value
	 */
	int getNewId();

	/**
	 * Persist brochure object into database
	 * @param temp Brochure object
	 */
	void save(Brochure temp);

	/**
	 * Find all the brochure from database
	 * @return List of brochure object
	 */
	List<Brochure> findAll();

	/**
	 * delete the brochure from the database
	 * @param temp brochure object
	 */
	void delete(Brochure temp);

	/**
	 * Find list of brochure object given onHome status value
	 * @param value boolean value
	 * @return list of brochure object
	 */
	List<Brochure> findByOnHome(boolean value);
	
	/**
	 * find brochure object given its unique id
	 * @param id int value
	 * @return brochure object
	 */
	Brochure findById(int id);

	/**
	 * Find all the brochure based on given category object
	 * @param cat category object
	 * @return list of brochure object
	 */
	List<Brochure> findByCategory(Category cat);
	
	List<Brochure> findAllBrochuresForCache();




}
