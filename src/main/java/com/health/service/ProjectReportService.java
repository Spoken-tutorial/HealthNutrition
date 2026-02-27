
package com.health.service;

import java.util.List;

import com.health.model.ProjectReport;
import com.health.model.StateDistrictMapping;

public interface ProjectReportService {

    ProjectReport findByProjectReportId(int projectReportId);

    List<ProjectReport> findByStateDistrictMapping(StateDistrictMapping stateDistrictMapping);

    List<ProjectReport> findByStateDistrictMappingInAndStatusTrue(List<StateDistrictMapping> stateDistrictMappingList);

    List<ProjectReport> findAll();

    List<ProjectReport> findAllByStatusTrue();

    void save(ProjectReport projectReport);

    void saveAll(List<ProjectReport> projectReportList);

}
