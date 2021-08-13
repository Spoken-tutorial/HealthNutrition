package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
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
	
	/**
	 * Find list of all Testimonial object given approve value
	 * @param value boolean value
	 * @return list of Testimonial object
	 */
	List<Testimonial> findByapproved(boolean value);

}
