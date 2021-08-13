package com.health.service;

import java.util.List;


import com.health.model.Carousel;
/**
 * This interface has all the method declaration related to Carousel object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CarouselService {

	/**
	 * Find the next unique id for brochure object
	 * @return primitive integer value
	 */
	int getNewId();

	/**
	 * Persist Carousel object into database
	 * @param temp Carousel object
	 */
	void save(Carousel temp);

	/**
	 * Find all the Carousel from database
	 * @return List of Carousel object
	 */
	List<Carousel> findAll();
	

	/**
	 * Find list of Carousel object given onHome status value
	 * @param value boolean value
	 * @return list of Carousel object
	 */
	List<Carousel> findByOnHome(boolean value);
	
	/**
	 * delete the Carousel from the database
	 * @param temp Carousel object
	 */
	void delete(Carousel temp);
}
