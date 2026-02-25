package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.District;
import com.health.model.State;
import com.health.repository.DistrictRepository;
import com.health.service.DistrictService;

/**
 * Default implementation of the {@link com.health.service.DistrictService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class DistrictServiceImpl implements DistrictService {
    private static final Logger logger = LoggerFactory.getLogger(DistrictServiceImpl.class);

    @Autowired
    private DistrictRepository distRepo;

    /**
     * @see com.health.service.DistrictService#save(District)
     */
    @Override
    public void save(District dist) {
        // TODO Auto-generated method stub
        distRepo.save(dist);
    }

    /**
     * @see com.health.service.DistrictService#findById(int)
     */
    @Override
    public District findById(int id) {
        // TODO Auto-generated method stub
        try {
            Optional<District> local = distRepo.findById(id);

            return local.get();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Id error in District Service Impl: {}", id, e);
            return null;
        }
    }

    /**
     * @see com.health.service.DistrictService#findAll()
     */
    @Override
    public List<District> findAll() {

        return distRepo.findAllOrderByName();
    }

    /**
     * @see com.health.service.DistrictService#findAllByState(State)
     */
    @Override
    public List<District> findAllByState(State state) {

        return distRepo.findAllByStateOrderByDistrictNameAsc(state);
    }

    @Override
    public Optional<District> findByDistrictNameIgnoreCase(String districtName) {

        return distRepo.findByDistrictNameIgnoreCase(districtName);
    }

}
