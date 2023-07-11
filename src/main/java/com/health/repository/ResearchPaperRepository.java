package com.health.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.health.model.ResearchPaper;


public interface ResearchPaperRepository extends CrudRepository<ResearchPaper, Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(id) from ResearchPaper")
	int getNewId();
	
	
	ResearchPaper findByTitle(String title);

	/**
	 * Find all the brochure based on given showOnHomePage field 
	 * @param value showOnHomePage field 
	 * @return List of brochure object
	 */
	List<ResearchPaper> findAllByshowOnHomepage(boolean value);
	
}
