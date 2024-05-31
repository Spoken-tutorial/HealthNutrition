package com.health.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.District;
import com.health.model.State;
import com.health.repository.StateRepository;
import com.health.service.StateService;

@Service
public class StateServiceImpl implements StateService {

    private static final Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);

    @Autowired
    private StateRepository stateRepo;

    @Override
    public void save(State state) {

        stateRepo.save(state);
    }

    @Override
    public State findById(int id) {

        try {
            Optional<State> local = stateRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error("Id error in State Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public List<State> findAll() {

        List<State> local = (List<State>) stateRepo.findAll();
        Collections.sort(local);
        return local;
    }

    @Override
    public State addStateToDistrict(State state, Set<District> districts) {

        state.getDistricts().addAll(districts);
        stateRepo.save(state);

        return null;
    }

}
