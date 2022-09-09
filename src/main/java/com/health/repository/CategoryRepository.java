package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.health.model.Category;

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
	@Query("select max(categoryId) from Category")
	int getNewId();
	
	/**
	 * Find category object given category name
	 * @param catname string object
	 * @return category object
	 */
	Category findBycatName(String catname);
	
	@Query(value="select category from Category category order by category.catName")
	List<Category> findAllByOrderBy();
	
	@Query(value="select * from Category s where s.category_name like %:keyword% or s.description like %:keyword% or(MATCH (category_name, description)\r\n"
			+ "AGAINST (%:keyword%))",
			nativeQuery = true)
	static
	List<Category> findByKeyword(@Param("keyword") String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
		// TODO Auto-generated method stub


}