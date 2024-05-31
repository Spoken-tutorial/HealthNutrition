package com.health.service;

import java.util.List;
import java.util.Set;

import com.health.model.District;
import com.health.model.State;

public interface StateService {

    void save(State state);

    State findById(int id);

    List<State> findAll();

    State addStateToDistrict(State state, Set<District> districts);
}
