
package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.ProjectReport;
import com.health.model.StateDistrictMapping;

public interface ProjectReportRepository extends JpaRepository<ProjectReport, Integer> {

    ProjectReport findByProjectReportId(int projectReportId);

    List<ProjectReport> findByStateDistrictMapping(StateDistrictMapping stateDistrictMapping);

    List<ProjectReport> findByStateDistrictMappingInAndStatusTrue(List<StateDistrictMapping> stateDistrictMappingList);

    List<ProjectReport> findAllByStatusTrue();

}
