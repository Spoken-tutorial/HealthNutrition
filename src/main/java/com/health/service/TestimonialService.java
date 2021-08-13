package com.health.service;

import java.util.List;

import com.health.model.Testimonial;
/**
 * This interface has all the method declaration related to Testimonial object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TestimonialService {
	
	/**
	 * Find All the Testimonial object from database
	 * @return list of Testimonial object
	 */
	List<Testimonial> findAll();   // in use
	
	/**
	 * delete Testimonial object from database given id
	 * @param id int value
	 */
    void deleteProduct(Integer id);
    
    /**
     * Find Testimonial object given id value
     * @param id int value
     * @return Testimonial object
     */
    Testimonial findById(int id);   // in use
    
    /**
	 * Find the next unique id for Testimonial object
	 * @return primitive integer value
	 */
	int getNewTestimonialId();
	
	/**
	 * Persist Testimonial object into database
	 * @param temp Testimonial object
	 */
	void save(Testimonial temp);   // in use
	
	/**
	 * List out Testimonial objects given approve field from database
	 * @param value boolean 
	 * @return list of Testimonial objects
	 */
	List<Testimonial> findByApproved(boolean value);
	
}
