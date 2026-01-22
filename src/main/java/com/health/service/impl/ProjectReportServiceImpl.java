
package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.ProjectReport;
import com.health.model.StateDistrictMapping;
import com.health.repository.ProjectReportRepository;
import com.health.service.ProjectReportService;

@Service
public class ProjectReportServiceImpl implements ProjectReportService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectReportServiceImpl.class);

    @Autowired
    private ProjectReportRepository repo;

    @Override
    public ProjectReport findByProjectReportId(int projectReportId) {

        return repo.findByProjectReportId(projectReportId);
    }

    @Override
    public List<ProjectReport> findByStateDistrictMapping(StateDistrictMapping stateDistrictMapping) {

        return repo.findByStateDistrictMapping(stateDistrictMapping);
    }

    @Override
    public List<ProjectReport> findByStateDistrictMappingInAndStatusTrue(
            List<StateDistrictMapping> stateDistrictMappingList) {

        return repo.findByStateDistrictMappingInAndStatusTrue(stateDistrictMappingList);
    }

    @Override
    public List<ProjectReport> findAll() {

        return repo.findAll();
    }

    @Override
    public List<ProjectReport> findAllByStatusTrue() {

        return repo.findAllByStatusTrue();
    }

    @Override
    public void save(ProjectReport projectReport) {
        repo.save(projectReport);

    }

    @Override
    public void saveAll(List<ProjectReport> projectReportList) {
        repo.saveAll(projectReportList);

    }

}
