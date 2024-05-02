package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.LiveTutorial;

public interface LiveTutorialRepository extends CrudRepository<LiveTutorial, Integer> {

    @Query("select max(id) from LiveTutorial")
    Integer getNewId();

}
