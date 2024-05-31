package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Brouchure;
import com.health.model.TopicCategoryMapping;

public interface BrouchureRepository extends CrudRepository<Brouchure, Integer> {

    @Query("select max(id) from Brouchure")
    int getNewId();

    Brouchure findByTitle(String title);

    List<Brouchure> findAllByshowOnHomepage(boolean value);

    @CacheEvict(cacheNames = "brouchures", allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(cacheNames = "brouchures", allEntries = true)
    <S extends Brouchure> S save(S entity);

    @Query("from Brouchure where topicCatId = ?1")
    List<Brouchure> findByTopicCat(TopicCategoryMapping topicCat);

    @Override
    List<Brouchure> findAll();
}
