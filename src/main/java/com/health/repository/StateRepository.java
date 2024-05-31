package com.health.repository;

import org.springframework.data.repository.CrudRepository;

import com.health.model.State;

public interface StateRepository extends CrudRepository<State, Integer> {

    State findBystateName(String name);

}
