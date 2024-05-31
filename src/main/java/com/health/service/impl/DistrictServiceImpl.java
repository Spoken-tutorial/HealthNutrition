package com.health.service.impl;

import java.util.Collections;
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

@Service
public class DistrictServiceImpl implements DistrictService {
    private static final Logger logger = LoggerFactory.getLogger(DistrictServiceImpl.class);

    @Autowired
    private DistrictRepository distRepo;

    @Override
    public void save(District dist) {

        distRepo.save(dist);
    }

    @Override
    public District findById(int id) {

        try {
            Optional<District> local = distRepo.findById(id);

            return local.get();

        } catch (Exception e) {

            logger.error("Id error in District Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public List<District> findAll() {

        List<District> local = (List<District>) distRepo.findAll();
        Collections.sort(local);
        return local;
    }

    @Override
    public List<District> findAllByState(State state) {

        List<District> local = distRepo.findAllBystate(state);
        Collections.sort(local);
        return local;
    }

}
