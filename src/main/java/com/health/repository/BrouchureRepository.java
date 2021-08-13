package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Brouchure;
import com.health.model.TopicCategoryMapping;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Brochure object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface BrouchureRepository extends CrudRepository<Brouchure, Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from Brouchure")
	int getNewId();

	/**
	 * Find all the brochure based on given showOnHomePage field 
	 * @param value showOnHomePage field 
	 * @return List of brochure object
	 */
	List<Brouchure> findAllByshowOnHomepage(boolean value);

	/**
	 * Find all the brochure based on given TopicCategory Object
	 * @param topicCat topicCategory object
	 * @return List of brochure object
	 */
	@Query("from Brouchure where topicCatId = ?1")
	List<Brouchure> findByTopicCat(TopicCategoryMapping topicCat);
}
