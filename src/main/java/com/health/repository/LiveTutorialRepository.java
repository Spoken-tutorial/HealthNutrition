package com.health.repository;

import org.springframework.data.repository.CrudRepository;

import com.health.model.LiveTutorial;

public interface LiveTutorialRepository extends CrudRepository<LiveTutorial, Integer> {

    LiveTutorial findByName(String row);

}
