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

/**
 * Default implementation of the {@link com.health.service.CityService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class CityServiceImpl implements CityService {
    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepo;

    /**
     * @see com.health.service.CityService#save(City)
     */
    @Override
    public void save(City city) {
        // TODO Auto-generated method stub
        cityRepo.save(city);

    }

    /**
     * @see com.health.service.CityService#findById(int)
     */
    @Override
    public City findById(int id) {
        // TODO Auto-generated method stub
        try {
            Optional<City> local = cityRepo.findById(id);
            return local.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Id error in City Service Impl class: {}", id, e);
            return null;
        }
    }

    /**
     * @see com.health.service.CityService#findAll()
     */
    @Override
    public List<City> findAll() {
        // TODO Auto-generated method stub
        List<City> local = (List<City>) cityRepo.findAll();
        Collections.sort(local);
        return local;
    }

    /**
     * @see com.health.service.CityService#findAllByDistrict(District)
     */
    @Override
    public List<City> findAllByDistrict(District district) {
        // TODO Auto-generated method stub
        List<City> local = cityRepo.findAllBydistrict(district);
        Collections.sort(local);
        return local;
    }

}
