package com.health.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.City;
import com.health.model.District;
import com.health.repository.CityRepository;
import com.health.service.CityService;

@Service
public class CityServiceImpl implements CityService {
    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepo;

    @Override
    public void save(City city) {

        cityRepo.save(city);

    }

    @Override
    public City findById(int id) {

        try {
            Optional<City> local = cityRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error("Id error in City Service Impl class: {}", id, e);
            return null;
        }
    }

    @Override
    public List<City> findAll() {

        List<City> local = (List<City>) cityRepo.findAll();
        Collections.sort(local);
        return local;
    }

    @Override
    public List<City> findAllByDistrict(District district) {

        List<City> local = cityRepo.findAllBydistrict(district);
        Collections.sort(local);
        return local;
    }

}
