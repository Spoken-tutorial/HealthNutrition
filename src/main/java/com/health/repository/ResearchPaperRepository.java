package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.ResearchPaper;

public interface ResearchPaperRepository extends CrudRepository<ResearchPaper, Integer> {

    @Query("select max(id) from ResearchPaper")
    int getNewId();

    ResearchPaper findByTitle(String title);

    List<ResearchPaper> findAllByshowOnHomepage(boolean value);

    List<ResearchPaper> findByShowOnHomepageIsTrueAndAddedQueueIsFalse();

    List<ResearchPaper> findByShowOnHomepageIsTrueAndAddedQueueIsTrue();

}
