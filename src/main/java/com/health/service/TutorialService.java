package com.health.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.Tutorial;

public interface TutorialService {

    Page<Tutorial> findAllByconAssignedTutorialListPagination(List<ContributorAssignedTutorial> con, Pageable page);

    Page<Tutorial> findAllByconAssignedTutorialPagination(ContributorAssignedTutorial con, Pageable page);

    Page<Tutorial> findAllPagination(Pageable page);

    List<Tutorial> findAll();

    int getNewId();

    Tutorial findByTutorialId(int tutorialId);

    List<Tutorial> findAllByContributorAssignedTutorial(ContributorAssignedTutorial con);

    List<Tutorial> findAllByContributorAssignedTutorialEnabled(ContributorAssignedTutorial con);

    List<Tutorial> findAllByContributorAssignedTutorialList(List<ContributorAssignedTutorial> con);

    List<Tutorial> findAllByContributorAssignedTutorialList1(List<ContributorAssignedTutorial> con);

    List<Tutorial> SearchOutlineByQuery(String query);

    Page<Tutorial> SearchOutlineByQuerPagination(String query, Pageable page);

    public Page<Tutorial> findBySearchCriteria(Specification<Tutorial> spec, Pageable page);

    public Page<Tutorial> SearchOutlineByCombinationOfWords(String query, Pageable page);

    void save(Tutorial tut);

    Tutorial getById(int id);

    List<Tutorial> findAllByStatus(boolean status);

    List<Tutorial> findAllByconAssignedTutorialAndStatus(List<ContributorAssignedTutorial> con);

    List<Tutorial> getFinalTutorialsForCache();

    Page<Tutorial> findAllPaginationWithEnabledCategoryandTrueTutorial(Pageable page);

    Page<Tutorial> SearchOutlineByCombinationOfWordsWithConAssisgendTutorials(List<ContributorAssignedTutorial> con,
            String query, Pageable page);

    Page<Tutorial> findPaginationWithEnabledCategoryandTrueTutorial(List<Tutorial> tutList, Pageable page);

    List<Tutorial> findByOutlinePathNull();

}