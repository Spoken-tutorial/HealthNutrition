package com.health.service;

import java.util.List;

import com.health.model.Brouchure;
import com.health.model.Category;
/**
 * This interface has all the method declaration related to Brochure object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface BrouchureService {

	/**
	 * Find the next unique id for brochure object
	 * @return primitive integer value
	 */
	int getNewId();

	/**
	 * Persist brochure object into database
	 * @param temp Brouchure object
	 */
	void save(Brouchure temp);

	/**
	 * Find all the brochure from database
	 * @return List of brochure object
	 */
	List<Brouchure> findAll();

	/**
	 * delete the brochure from the database
	 * @param temp brochure object
	 */
	void delete(Brouchure temp);

	/**
	 * Find list of brochure object given onHome status value
	 * @param value boolean value
	 * @return list of brochure object
	 */
	List<Brouchure> findByOnHome(boolean value);
	
	/**
	 * find brochure object given its unique id
	 * @param id int value
	 * @return brochure object
	 */
	Brouchure findById(int id);

	/**
	 * Find all the brochure based on given category object
	 * @param cat category object
	 * @return list of brochure object
	 */
	List<Brouchure> findByCategory(Category cat);




}
