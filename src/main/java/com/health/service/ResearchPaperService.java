package com.health.service;

import java.util.List;

import com.health.model.ResearchPaper;

public interface ResearchPaperService {

    int getNewId();

    void save(ResearchPaper temp);

    List<ResearchPaper> findAll();

    void delete(ResearchPaper temp);

    List<ResearchPaper> findByOnHome(boolean value);

    ResearchPaper findById(int id);

    List<ResearchPaper> findAllByShowOnHomePage();

    List<ResearchPaper> findByShowOnHomepageIsTrueAndAddedQueueIsFalse();

    List<ResearchPaper> findByShowOnHomepageIsTrueAndAddedQueueIsTrue();

}
