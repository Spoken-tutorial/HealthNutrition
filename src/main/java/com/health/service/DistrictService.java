package com.health.service;

import java.util.List;

import com.health.model.District;
import com.health.model.State;

public interface DistrictService {

    void save(District dist);

    District findById(int id);

    List<District> findAll();

    List<District> findAllByState(State state);

}
