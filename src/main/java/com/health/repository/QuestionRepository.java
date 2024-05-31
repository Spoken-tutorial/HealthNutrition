package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Language;
import com.health.model.Question;
import com.health.model.TopicCategoryMapping;

public interface QuestionRepository extends CrudRepository<Question, Integer> {

    @Query("select max(questionId) from Question")
    int getNewId();

    @Query("from Question where topicCatId = ?1 and lan = ?2")
    Question findByTopicLan(TopicCategoryMapping topicCat, Language lan);

}
