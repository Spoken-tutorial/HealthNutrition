package com.health.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.Tutorial;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Tutorial object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> , JpaSpecificationExecutor<Tutorial>{

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(tutorialId) from Tutorial")
	int getNewId();
	
	/**
	 * List of Tutorial Object given ContributorAssignedTutorial object
	 * @param con ContributorAssignedTutorial object
	 * @return list of Tutorial object
	 */
	//Changes are done by Alok
	//@Query("from Tutorial t where t.status = true and t.conAssignedTutorial = ?1")
	@Query("from Tutorial t where  t.conAssignedTutorial = ?1")
	List<Tutorial> findAllByconAssignedTutorial(ContributorAssignedTutorial con);
	
	//New Function By Alok
	@Query("from Tutorial t where t.status = true and t.conAssignedTutorial = ?1")
	List<Tutorial> findAllByconAssignedTutorialByStatusTrue(ContributorAssignedTutorial con);
	
	//New function By Alok to find tutorial by search outline
	@Query("select t from Tutorial t where t.outline  LIKE %?1%")
	List<Tutorial> findByOutlineByQuery(String query);
	
	//New function By Alok to find tutorial by search outline query and page
	@Query("from Tutorial t where t.outline  LIKE %?1%")
	Page<Tutorial> findByOutlineByQueryPagination( String query,Pageable page);
	
	
	
	/**
	 * List of Tutorial Object given status value
	 * @param status boolean value
	 * @return list of Tutorial object
	 */
	List<Tutorial> findAllBystatus(boolean status);
	
	/**
	 * List out all the tutorial based on pagination
	 * @param page Pageable object
	 * @return Page object
	 */
	@Query("from Tutorial t where t.status = true")
	Page<Tutorial> findAllByPagination(Pageable page);
	
	/**
	 * List out all the tutorial given ContributorAssignedTutorial object under pagination concept
	 * @param con ContributorAssignedTutorial object
	 * @param page Pageable object
	 * @return page object
	 */
	@Query("from Tutorial t where t.status = true and t.conAssignedTutorial = ?1")
	Page<Tutorial> findAllByconAssignedTutorialPagination(ContributorAssignedTutorial con,Pageable page);
	
	
	@Query("from Tutorial t where t.conAssignedTutorial IN (:con) and t.status = true")
	public Page<Tutorial> findAllByconAssignedTutorialListPagination(@Param("con")List<ContributorAssignedTutorial> con,Pageable page);
	
	@Query("from Tutorial t where t.conAssignedTutorial IN (:con) and t.status = true")
	public List<Tutorial> findAllByconAssignedTutorialAndStatus(@Param("con")List<ContributorAssignedTutorial> con);
	

}