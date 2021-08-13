package com.health.service;

import java.util.List;

import com.health.model.Category;
import com.health.model.Event;

/**
 * This interface has all the method declaration related to Category object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CategoryService {
	
	/**
	 * Find all the Category from database
	 * @return List of brochure object
	 */
	List<Category> findAll();
	
	/**
	 * Find Category object  based on given category name
	 * @param name String value
	 * @return Category object
	 */
	Category findBycategoryname(String name);
	
	/**
	 * delete the Category from the database
	 * @param id primitive integer value
	 */
    void deleteProduct(Integer id);
   
    /**
	 * find Category object given its unique id
	 * @param id int value
	 * @return Category object
	 */
    Category findByid(int id);
	
    /**
     * update category object in database
     * @param description desc of category object
     * @param id id of category object
     * @return boolean value
     */
	Boolean updateCategory(String description,int id);
	
	/**
	 * Find the next unique id for Category object
	 * @return primitive integer value
	 */
	int getNewCatId();
	
	/**
	 * Persist Category object into database
	 * @param cat category object
	 */
	Category save(Category cat);
	
    
    
}
