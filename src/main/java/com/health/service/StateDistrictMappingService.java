
package com.health.service;

import java.util.List;

import com.health.model.District;
import com.health.model.State;
import com.health.model.StateDistrictMapping;

public interface StateDistrictMappingService {

    StateDistrictMapping findByStateDistrictId(int stateDisId);

    List<StateDistrictMapping> findByDistrict(District district);

    List<StateDistrictMapping> findByState(State state);

    StateDistrictMapping findByStateAndDistrict(State state, District district);

    List<StateDistrictMapping> findAll();

    void save(StateDistrictMapping stateDistrictMapping);

    void saveAll(List<StateDistrictMapping> stateDistrictMappingList);

}
