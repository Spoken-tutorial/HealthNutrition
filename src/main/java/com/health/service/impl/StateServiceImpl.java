package com.health.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.District;
import com.health.model.State;
import com.health.repository.StateRepository;
import com.health.service.StateService;
/**
 * Default implementation of the {@link com.health.service.StateService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class StateServiceImpl implements StateService {

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
			Optional<State> local=stateRepo.findById(id);
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.health.service.StateService#findAll()
	 */
	@Override
	public List<State> findAll() {
		// TODO Auto-generated method stub
		List<State> local=(List<State>) stateRepo.findAll();
		Collections.sort(local);
		return local;
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
