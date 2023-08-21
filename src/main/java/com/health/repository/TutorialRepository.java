package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.Tutorial;

/**
 * This Interface Extend CrudRepository to handle all database operation related
 * to Tutorial object
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface TutorialRepository extends JpaRepository<Tutorial, Integer>, JpaSpecificationExecutor<Tutorial> {

    /**
     * Find the next unique id for the object
     * 
     * @return primitive integer value
     */
    @Query("select max(tutorialId) from Tutorial")
    int getNewId();

    /**
     * List of Tutorial Object given ContributorAssignedTutorial object
     * 
     * @param con ContributorAssignedTutorial object
     * @return list of Tutorial object
     */
    // Changes are done by Alok
    // @Query("from Tutorial t where t.status = true and t.conAssignedTutorial =
    // ?1")
    @Query("from Tutorial t where  t.conAssignedTutorial = ?1")
    List<Tutorial> findAllByconAssignedTutorial(ContributorAssignedTutorial con);

    // New Function By Alok
    @Query("from Tutorial t where t.status = true and t.conAssignedTutorial = ?1")
    List<Tutorial> findAllByconAssignedTutorialByStatusTrue(ContributorAssignedTutorial con);

    // New function By Alok to find tutorial by search outline
    @Query("select t from Tutorial t where t.outline  LIKE %?1%")
    List<Tutorial> findByOutlineByQuery(String query);

    // New function By Alok to find tutorial by search outline query and page
    // @Query("SELECT t FROM Tutorial t WHERE " + "t.outline LIKE CONCAT('%',:query,
    // '%')" +"Or t.topicName LIKE CONCAT('%', :query, '%')")
    @Query("from Tutorial t where t.outline  LIKE %?1%")
    Page<Tutorial> findByOutlineByQueryPagination(String query, Pageable page);

    @Query("from Tutorial t where t.outline  LIKE %:query1% and  t.outline  LIKE %:query2%")
    Page<Tutorial> findByOutlineByQuerywords2(@Param("query1") String query1, @Param("query2") String query2,
            Pageable page);

    @Query("from Tutorial t where t.outline  LIKE %:query1%  and  t.outline  LIKE %:query2%   and t.outline LIKE %:query3%")
    Page<Tutorial> findByOutlineByQuerywords3(@Param("query1") String query1, @Param("query2") String query2,
            @Param("query3") String query3, Pageable page);

    @Query("from Tutorial t where t.outline LIKE %:query1% and  t.outline  LIKE %:query2%  and t.outline LIKE %:query3% and t.outline  LIKE %:query4%")
    Page<Tutorial> findByOutlineByQuerywords4(@Param("query1") String query1, @Param("query2") String query2,
            @Param("query3") String query3, @Param("query4") String query4, Pageable page);

    @Query("from Tutorial t where t.outline  LIKE %:query1% and  t.outline  LIKE %:query2% and t.outline  LIKE %:query3% and t.outline  LIKE %:query4% and t.outline LIKE %:query5%")
    Page<Tutorial> findByOutlineByQuerywords5(@Param("query1") String query1, @Param("query2") String query2,
            @Param("query3") String query3, @Param("query4") String query4, @Param("query5") String query5,
            Pageable page);

    Page<Tutorial> findByOutlineContainingIgnoreCase(String query, Pageable pageable);

    /*
     * @Query("from Tutorial t where  t IN (:tutorials) and  t.outline  LIKE %?1%")
     * Page<Tutorial>
     * findByOutlineByQueryPaginationInTutorialList(@Param("tutorials")List<
     * Tutorial> tutorials, String query, Pageable page);
     * 
     * @Query("from Tutorial t where  t IN (:tutorials) and  t.outline  LIKE %:query1% and  t.outline  LIKE %:query2%"
     * ) Page<Tutorial>
     * findByOutlineByQuerywords2InTutorialList(@Param("tutorials")List<Tutorial>
     * tutorials, @Param("query1") String query1, @Param("query2") String query2,
     * Pageable page);
     * 
     * @Query("from Tutorial t where  t IN (:tutorials) and  t.outline  LIKE %:query1%  and  t.outline  LIKE %:query2%   and t.outline LIKE %:query3%"
     * ) Page<Tutorial>
     * findByOutlineByQuerywords3InTutorialList(@Param("tutorials")List<Tutorial>
     * tutorials, @Param("query1") String query1, @Param("query2") String
     * query2, @Param("query3") String query3, Pageable page);
     * 
     * @Query("from Tutorial t where  t IN (:tutorials) and  t.outline LIKE %:query1% and  t.outline  LIKE %:query2%  and t.outline LIKE %:query3% and t.outline  LIKE %:query4%"
     * ) Page<Tutorial>
     * findByOutlineByQuerywords4InTutorialList(@Param("tutorials")List<Tutorial>
     * tutorials, @Param("query1") String query1, @Param("query2") String
     * query2, @Param("query3") String query3, @Param("query4") String query4,
     * Pageable page);
     * 
     * @Query("from Tutorial t where t IN (:tutorials) and  t.outline  LIKE %:query1% and  t.outline  LIKE %:query2% and t.outline  LIKE %:query3% and t.outline  LIKE %:query4% and t.outline LIKE %:query5%"
     * ) Page<Tutorial>
     * findByOutlineByQuerywords5InTutorialList(@Param("tutorials")List<Tutorial>
     * tutorials, @Param("query1") String query1, @Param("query2") String
     * query2, @Param("query3") String query3,@Param("query4") String
     * query4, @Param("query5") String query5, Pageable page);
     * 
     */

    @Query("from Tutorial t where  t.conAssignedTutorial IN (:con) and t.status = true and  t.outline  LIKE %:query1%")
    Page<Tutorial> findByOutlineByQueryPaginationconAssignedTutorialList(
            @Param("con") List<ContributorAssignedTutorial> con, @Param("query1") String query1, Pageable page);

    @Query("from Tutorial t where t.conAssignedTutorial IN (:con) and t.status = true and  t.outline  LIKE %:query1% and  t.outline  LIKE %:query2%")
    Page<Tutorial> findByOutlineByQuerywords2conAssignedTutorialList(
            @Param("con") List<ContributorAssignedTutorial> con, @Param("query1") String query1,
            @Param("query2") String query2, Pageable page);

    @Query("from Tutorial t where  t.conAssignedTutorial IN (:con) and t.status = true and  t.outline  LIKE %:query1%  and  t.outline  LIKE %:query2%   and t.outline LIKE %:query3%")
    Page<Tutorial> findByOutlineByQuerywords3conAssignedTutorialList(
            @Param("con") List<ContributorAssignedTutorial> con, @Param("query1") String query1,
            @Param("query2") String query2, @Param("query3") String query3, Pageable page);

    @Query("from Tutorial t where t.conAssignedTutorial IN (:con) and t.status = true and  t.outline LIKE %:query1% and  t.outline  LIKE %:query2%  and t.outline LIKE %:query3% and t.outline  LIKE %:query4%")
    Page<Tutorial> findByOutlineByQuerywords4conAssignedTutorialList(
            @Param("con") List<ContributorAssignedTutorial> con, @Param("query1") String query1,
            @Param("query2") String query2, @Param("query3") String query3, @Param("query4") String query4,
            Pageable page);

    @Query("from Tutorial t where t.conAssignedTutorial IN (:con) and t.status = true and  t.outline  LIKE %:query1% and  t.outline  LIKE %:query2% and t.outline  LIKE %:query3% and t.outline  LIKE %:query4% and t.outline LIKE %:query5%")
    Page<Tutorial> findByOutlineByQuerywords5conAssignedTutorialList(
            @Param("con") List<ContributorAssignedTutorial> con, @Param("query1") String query1,
            @Param("query2") String query2, @Param("query3") String query3, @Param("query4") String query4,
            @Param("query5") String query5, Pageable page);

    /**
     * List of Tutorial Object given status value
     * 
     * @param status boolean value
     * @return list of Tutorial object
     */
    // @Cacheable(cacheNames ="tutorials" )
    // List<Tutorial> findAllByStatusTrue();

    // @Cacheable(cacheNames ="tutorials" )
    List<Tutorial> findAllByStatus(boolean status);

    // @CacheEvict(cacheNames = "tutorials", allEntries=true)
    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    void deleteById(int id);

    // @CacheEvict(cacheNames = "tutorials", allEntries=true)
    @Override
    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    <S extends Tutorial> S save(S entity);

    /**
     * List out all the tutorial based on pagination
     * 
     * @param page Pageable object
     * @return Page object
     */
    @Query("from Tutorial t where t.status = true")
    Page<Tutorial> findAllByPagination(Pageable page);

    /**
     * List out all the tutorial given ContributorAssignedTutorial object under
     * pagination concept
     * 
     * @param con  ContributorAssignedTutorial object
     * @param page Pageable object
     * @return page object
     */
    @Query("from Tutorial t where t.status = true and t.conAssignedTutorial = ?1")
    Page<Tutorial> findAllByconAssignedTutorialPagination(ContributorAssignedTutorial con, Pageable page);

    @Query("from Tutorial t where t.conAssignedTutorial IN (:con) and t.status = true")
    public Page<Tutorial> findAllByconAssignedTutorialListPagination(
            @Param("con") List<ContributorAssignedTutorial> con, Pageable page);

    @Query("from Tutorial t where t.conAssignedTutorial IN (:con) and t.status = true")
    public List<Tutorial> findAllByconAssignedTutorialAndStatus(@Param("con") List<ContributorAssignedTutorial> con);

}