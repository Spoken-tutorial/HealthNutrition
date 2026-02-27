
package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.District;
import com.health.model.State;
import com.health.model.StateDistrictMapping;

public interface StateDistrictMappingRepository extends JpaRepository<StateDistrictMapping, Integer> {

    StateDistrictMapping findByStateDisId(int stateDisId);

    List<StateDistrictMapping> findByDistrict(District district);

    List<StateDistrictMapping> findByState(State state);

    StateDistrictMapping findByStateAndDistrict(State state, District district);

}
