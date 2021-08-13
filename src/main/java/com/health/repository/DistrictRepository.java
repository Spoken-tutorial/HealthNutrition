package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Category;
import com.health.model.District;
import com.health.model.State;
import com.health.model.Tutorial;
import com.health.model.User;


/**
 * This Interface Extend CrudRepository to handle all database operation related to District object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface DistrictRepository extends CrudRepository<District,Integer> {
	
	/**
	 * Find District object given unique id
	 * @param id int
	 * @return District object
	 */
     District findByid(int id);
	
     /**
      * Find District object given district name
      * @param name String value
      * @return District object
      */
     District findBydistrictName(String name);
 	
     /**
      * find list of District object given state object
      * @param state state object
      * @return list of District object
      */
     List<District> findAllBystate(State state);
	  
	 
	
}
