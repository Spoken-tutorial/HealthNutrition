package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Category;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Category object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CategoryRepository extends CrudRepository<Category,Integer>{

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(categoryId) from Category")
	int getNewId();
	
	/**
	 * Find category object given category name
	 * @param catname string object
	 * @return category object
	 */
	Category findBycatName(String catname);

}