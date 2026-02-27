package com.health.service.impl;

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

/**
 * Default implementation of the {@link com.health.service.StateService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class StateServiceImpl implements StateService {

    private static final Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);

    @Autowired
    private StateRepository stateRepo;

    /**
     * @see com.health.service.StateService#save(State)
     */
    @Override
    public void save(State state) {
        // TODO Auto-generated method stub
        stateRepo.save(state);
    }

    /**
     * @see com.health.service.StateService#findById(int)
     */
    @Override
    public State findById(int id) {
        // TODO Auto-generated method stub
        try {
            Optional<State> local = stateRepo.findById(id);
            return local.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Id error in State Service Impl: {}", id, e);
            return null;
        }
    }

    /**
     * @see com.health.service.StateService#findAll()
     */
    @Override
    public List<State> findAll() {

        return stateRepo.findAllOrderByName();

    }

    /**
     * @see com.health.service.StateService#addStateToDistrict(State, Set)
     */
    @Override
    public State addStateToDistrict(State state, Set<District> districts) {
        // TODO Auto-generated method stub

        state.getDistricts().addAll(districts);
        stateRepo.save(state);

        return null;
    }

}
