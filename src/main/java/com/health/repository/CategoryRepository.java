package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.health.model.Category;
import java.util.Optional;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Category object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CategoryRepository extends JpaRepository<Category,Integer>{

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	//@Cacheable(cacheNames = "categories")
	@Query("select max(categoryId) from Category")
	int getNewId();
	
	/**
	 * Find category object given category name
	 * @param catname string object
	 * @return category object
	 */
	@CacheEvict(cacheNames = "categories", allEntries=true)
	void deleteById(int id);
	
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	<S extends Category> S save(S entity);
	
	//@Cacheable(cacheNames = "categories", key="#catname")
	Category findBycatName(String catname);
	
	//@Cacheable(cacheNames = "categories", key="#id")
	Optional<Category> findById(int id);
	
	@Query(value="select category from Category category order by category.catName")
	//@Cacheable(cacheNames = "categories")
	List<Category> findAllByOrderBy();
	
	
	

}