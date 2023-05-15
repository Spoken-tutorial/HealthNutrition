package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Brochure;
import com.health.model.TopicCategoryMapping;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Brochure object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface BrochureRepository extends CrudRepository<Brochure, Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from Brochure")
	int getNewId();
	
	
	Brochure findByTitle(String title);

	/**
	 * Find all the brochure based on given showOnHomePage field 
	 * @param value showOnHomePage field 
	 * @return List of brochure object
	 */
	List<Brochure> findAllByshowOnHomepage(boolean value);
	
	@CacheEvict(cacheNames = "brochures", allEntries=true)
	void deleteById(int id);
	
	@CacheEvict(cacheNames = "brochures", allEntries=true)
	<S extends Brochure> S save(S entity);

	/**
	 * Find all the brochure based on given TopicCategory Object
	 * @param topicCat topicCategory object
	 * @return List of brochure object
	 */
	@Query("from Brochure where topicCatId = ?1")
	List<Brochure> findByTopicCat(TopicCategoryMapping topicCat);
	
	//@Cacheable(cacheNames ="brochures" )
	List<Brochure> findAll();
}
