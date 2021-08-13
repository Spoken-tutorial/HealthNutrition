package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.City;
import com.health.model.District;
import com.health.model.State;

/**
 * This Interface Extend CrudRepository to handle all database operation related to City object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CityRepository extends CrudRepository<City, Integer>{
	
	/**
	 * Find list of City object given district object
	 * @param district district object
	 * @return list of city object
	 */
	  List<City> findAllBydistrict(District district);
	  
	  /**
	   * find city object given district object and city name
	   * @param district district object
	   * @param cityname string object
	   * @return  city object
	   */
	  @Query("from City where district=?1 and cityName=?2")
	  City findBydistrictandCity(District district,String cityname);
	  
	  /**
	   * find district given city name
	   * @param name string name 
	   * @return District object
	   */
	  District findBycityName(String name);
	    
}
