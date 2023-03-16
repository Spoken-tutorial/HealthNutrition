package com.health.repository;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.health.model.Language;
import com.health.model.Topic;

/**
 * This Interface Extend JpaRepository to handle all database operation related to Language object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface LangaugeRepository extends JpaRepository<Language,Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	//@Cacheable(cacheNames = "languages")
	@Query("select max(lanId) from Language")
	int getNewId();
	
	/**
	 * Find Language object given lang name
	 * @param langName String value
	 * @return Langauge object
	 */
	
	//@Cacheable(cacheNames = "languages", key="#langName")
	Language findBylangName(String langName);
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	void deleteById(int lanId);
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	<S extends Language> S save(S entity);
	
	//@Cacheable(cacheNames = "languages", key="#lanId")
	Optional<Language> findById( int lanId);
	
	
}
