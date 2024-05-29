package com.health.service;

import java.util.List;

import com.health.model.ResearchPaper;

public interface ResearchPaperService {

    /**
     * Find the next unique id for brochure object
     * 
     * @return primitive integer value
     */
    int getNewId();

    /**
     * Persist brochure object into database
     * 
     * @param temp Brouchure object
     */
    void save(ResearchPaper temp);

    /**
     * Find all the brochure from database
     * 
     * @return List of brochure object
     */
    List<ResearchPaper> findAll();

    /**
     * delete the brochure from the database
     * 
     * @param temp brochure object
     */
    void delete(ResearchPaper temp);

    /**
     * Find list of brochure object given onHome status value
     * 
     * @param value boolean value
     * @return list of brochure object
     */
    List<ResearchPaper> findByOnHome(boolean value);

    /**
     * find brochure object given its unique id
     * 
     * @param id int value
     * @return brochure object
     */
    ResearchPaper findById(int id);

    List<ResearchPaper> findAllByShowOnHomePage();

    List<ResearchPaper> findByShowOnHomepageIsTrueAndAddedQueueIsFalse();
    List<ResearchPaper> findByShowOnHomepageIsTrueAndAddedQueueIsTrue();

}
