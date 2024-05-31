package com.health.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.health.model.District;
import com.health.model.State;

public interface DistrictRepository extends CrudRepository<District, Integer> {

    District findByid(int id);

    District findBydistrictName(String name);

    List<District> findAllBystate(State state);

}
