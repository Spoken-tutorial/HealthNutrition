package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.FeedbackForm;

public interface FeedbackContactFormRepository extends CrudRepository<FeedbackForm, Integer> {

    @Query("select max(id) from FeedbackForm")
    int getNewId();

}
