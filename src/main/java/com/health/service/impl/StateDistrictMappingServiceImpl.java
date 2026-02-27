
package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.District;
import com.health.model.State;
import com.health.model.StateDistrictMapping;
import com.health.repository.StateDistrictMappingRepository;
import com.health.service.StateDistrictMappingService;

@Service
public class StateDistrictMappingServiceImpl implements StateDistrictMappingService {

    private static final Logger logger = LoggerFactory.getLogger(StateDistrictMappingServiceImpl.class);

    @Autowired
    private StateDistrictMappingRepository repo;

    @Override
    public StateDistrictMapping findByStateDistrictId(int stateDisId) {

        return repo.findByStateDisId(stateDisId);
    }

    @Override
    public List<StateDistrictMapping> findByDistrict(District district) {

        return repo.findByDistrict(district);
    }

    @Override
    public List<StateDistrictMapping> findByState(State state) {

        return repo.findByState(state);
    }

    @Override
    public StateDistrictMapping findByStateAndDistrict(State state, District district) {

        return repo.findByStateAndDistrict(state, district);
    }

    @Override
    public List<StateDistrictMapping> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(StateDistrictMapping stateDistrictMapping) {
        repo.save(stateDistrictMapping);

    }

    @Override
    public void saveAll(List<StateDistrictMapping> stateDistrictMappingList) {
        repo.saveAll(stateDistrictMappingList);

    }

}
