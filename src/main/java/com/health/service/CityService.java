package com.health.service;

import java.util.List;

import com.health.model.City;
import com.health.model.District;

/**
 * This interface has all the method declaration related to City object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CityService {

	/**
	 * Persist City object into database
	 * @param city City object
	 */
	void save(City city);
	
	/**
	 * Find out City object given city Id
	 * @param id int value
	 * @return City object
	 */
	City findById(int id);
	
	/**
	 * List out all the City object from database
	 * @return List of City object
	 */
	List<City> findAll();
	
	/**
	 * Find list of City object given district object
	 * @param district district object
	 * @return list of city object
	 */
	List<City> findAllByDistrict(District district);
}
