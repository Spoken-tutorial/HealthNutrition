package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Testimonial;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Testimonial object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TestimonialRepository extends CrudRepository<Testimonial, Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(testimonialId) from Testimonial")
	int getNewId();
	
	
	@CacheEvict(cacheNames = "testimonials", allEntries=true)
	void deleteById(int id);
	
	@CacheEvict(cacheNames = "testimonials", allEntries=true)
	<S extends Testimonial> S save(S entity);

	
	/**
	 * Find list of all Testimonial object given approve value
	 * @param value boolean value
	 * @return list of Testimonial object
	 */
	//@Cacheable(cacheNames ="testimonials", key="#value")
	List<Testimonial> findByapproved(boolean value);
	
	//@Cacheable(cacheNames ="testimonials")
	List<Testimonial> findAll();

}
