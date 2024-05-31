package com.health.service;

import java.util.List;

import com.health.model.City;
import com.health.model.District;

public interface CityService {

    void save(City city);

    City findById(int id);

    List<City> findAll();

    List<City> findAllByDistrict(District district);
}
