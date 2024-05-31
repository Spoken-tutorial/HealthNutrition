package com.health.repository;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Topic;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

    @Query("select max(topicId) from Topic")
    int getNewId();

    Topic findBytopicName(String topicName);

    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    <S extends Topic> S save(S entity);

    Optional<Topic> findById(int id);

}
