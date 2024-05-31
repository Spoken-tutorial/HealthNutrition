package com.health.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.health.model.TopicCategoryMapping;
import com.health.model.TrainingTopic;

public interface TrainingTopicRepository extends CrudRepository<TrainingTopic, Integer> {

    @Query("select max(trainingTopicId) from TrainingTopic")
    int getNewId();

    @Query("from TrainingTopic where topicCatId IN (:TopicCat)")
    Set<TrainingTopic> findByTopicCat(@Param("TopicCat") List<TopicCategoryMapping> topCat);

}
