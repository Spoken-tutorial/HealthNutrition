package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.Language;
import com.health.model.TopicCategoryMapping;

public interface ContributorAssignedTutorialRepository extends CrudRepository<ContributorAssignedTutorial, Integer> {

    @Query("select max(id) from ContributorAssignedTutorial")
    int getNewId();

    List<ContributorAssignedTutorial> findAllBytopicCatId(TopicCategoryMapping topCat);

    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    <S extends ContributorAssignedTutorial> S save(S entity);

    List<ContributorAssignedTutorial> findAllBylan(Language lan);

    @Query("from ContributorAssignedTutorial where topicCatId=?1 and lan=?2")
    ContributorAssignedTutorial findByTopicCatLan(TopicCategoryMapping topicCat, Language lan);

    @Query("from ContributorAssignedTutorial where topicCatId IN (:TopicCat)")
    List<ContributorAssignedTutorial> findByTopicCat(@Param("TopicCat") List<TopicCategoryMapping> topCat);
}