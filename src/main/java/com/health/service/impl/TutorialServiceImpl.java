package com.health.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.health.model.Category;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Tutorial;
import com.health.repository.TutorialRepository;
import com.health.service.TutorialService;

@Service
public class TutorialServiceImpl implements TutorialService {

    private static final Logger logger = LoggerFactory.getLogger(TutorialServiceImpl.class);

    @Autowired
    private TutorialRepository tutorialRepo;

    @Override
    public List<Tutorial> findAll() {

        return tutorialRepo.findAll();
    }

    @Override
    @Cacheable(cacheNames = "tutorials")
    public List<Tutorial> getFinalTutorialsForCache() {

        List<Tutorial> tutorials = tutorialRepo.findAllByStatus(true);
        List<Tutorial> finalTutorials = new ArrayList<>();
        for (Tutorial temp : tutorials) {

            Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
            if (c.isStatus()) {

                finalTutorials.add(temp);
            }
        }

        return finalTutorials;
    }

    @Override
    public int getNewId() {

        try {
            return tutorialRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Tutorial Service Impl: {}", tutorialRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public List<Tutorial> findAllByContributorAssignedTutorial(ContributorAssignedTutorial con) {

        return tutorialRepo.findAllByconAssignedTutorial(con);
    }

    @Override
    public Tutorial findByTutorialId(int tutorialId) {
        return tutorialRepo.findByTutorialId(tutorialId);
    }

    @Override
    public List<Tutorial> findByOutlinePathNull() {

        return tutorialRepo.findByOutlinePathNull();
    }

    @Override
    public List<Tutorial> findAllByContributorAssignedTutorialEnabled(ContributorAssignedTutorial con) {

        return tutorialRepo.findAllByconAssignedTutorialByStatusTrue(con);
    }

    @Override
    public void save(Tutorial tut) {

        tutorialRepo.save(tut);
    }

    @Override
    public Tutorial getById(int id) {

        try {
            Optional<Tutorial> local = tutorialRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error("Id error in Tutorial Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public List<Tutorial> findAllByContributorAssignedTutorialList(List<ContributorAssignedTutorial> con) {

        List<Tutorial> localList = new ArrayList<Tutorial>();
        for (ContributorAssignedTutorial conTemp : con) {
            localList.addAll(tutorialRepo.findAllByconAssignedTutorial(conTemp));
        }
        return localList;

    }

    @Override
    public List<Tutorial> findAllByContributorAssignedTutorialList1(List<ContributorAssignedTutorial> con) {

        List<Tutorial> localList = new ArrayList<Tutorial>();
        for (ContributorAssignedTutorial conTemp : con) {
            localList.addAll(tutorialRepo.findAllByconAssignedTutorialByStatusTrue(conTemp));
        }
        return localList;

    }

    @Override
    public List<Tutorial> findAllByStatus(boolean status) {

        return tutorialRepo.findAllByStatus(status);
    }

    @Override
    public Page<Tutorial> findAllPagination(Pageable page) {

        return tutorialRepo.findAll(page);
    }

    @Override
    public Page<Tutorial> findAllPaginationWithEnabledCategoryandTrueTutorial(Pageable page) {

        List<Tutorial> tutorials = new ArrayList<>();

        List<Tutorial> tutList = tutorialRepo.findAll();
        for (Tutorial temp : tutList) {
            if (temp.getConAssignedTutorial().getTopicCatId().getCat().isStatus() && temp.isStatus()) {
                tutorials.add(temp);
            }
        }

        final int start = (int) page.getOffset();
        final int end = Math.min((start + page.getPageSize()), tutorials.size());
        Page<Tutorial> pageOfTutorials = new PageImpl<>(tutorials.subList(start, end), page, tutorials.size());

        return pageOfTutorials;
    }

    @Override
    public Page<Tutorial> findPaginationWithEnabledCategoryandTrueTutorial(List<Tutorial> tutList, Pageable page) {

        List<Tutorial> tutorials = new ArrayList<>();

        for (Tutorial temp : tutList) {
            if (temp.getConAssignedTutorial().getTopicCatId().getCat().isStatus() && temp.isStatus()) {
                tutorials.add(temp);
            }
        }

        final int start = (int) page.getOffset();
        final int end = Math.min((start + page.getPageSize()), tutorials.size());
        Page<Tutorial> pageOfTutorials = new PageImpl<>(tutorials.subList(start, end), page, tutorials.size());

        return pageOfTutorials;
    }

    @Override
    public Page<Tutorial> findAllByconAssignedTutorialPagination(ContributorAssignedTutorial con, Pageable page) {

        return tutorialRepo.findAllByconAssignedTutorialPagination(con, page);

    }

    @Override
    public Page<Tutorial> findAllByconAssignedTutorialListPagination(List<ContributorAssignedTutorial> con,
            Pageable page) {

        return tutorialRepo.findAllByconAssignedTutorialListPagination(con, page);
    }

    @Override
    public List<Tutorial> findAllByconAssignedTutorialAndStatus(List<ContributorAssignedTutorial> con) {

        return tutorialRepo.findAllByconAssignedTutorialAndStatus(con);
    }

    @Override
    public List<Tutorial> SearchOutlineByQuery(String query) {

        if (query != null)
            return tutorialRepo.findByOutlineByQuery(query);
        else
            return tutorialRepo.findAll();

    }

    @Override
    public Page<Tutorial> SearchOutlineByQuerPagination(String query, Pageable page) {

        return tutorialRepo.findByOutlineByQueryPagination(query, page);
    }

    @Override
    public Page<Tutorial> findBySearchCriteria(Specification<Tutorial> spec, Pageable page) {
        Page<Tutorial> searchResult = tutorialRepo.findAll(spec, page);
        return searchResult;
    }

    @Override
    public Page<Tutorial> SearchOutlineByCombinationOfWords(String query, Pageable page) {
        Page<Tutorial> tutorialPageList = null;

        List<String> qList = new ArrayList<String>(Arrays.asList(query.split(" ")));
        int queryLength = qList.size();
        if (queryLength > 5) {
            queryLength = 5;
        }

        tutorialPageList = tutorialRepo.findByOutlineByQueryPagination(query, page);

        if (!tutorialPageList.isEmpty()) {
            return tutorialPageList;
        }

        else {

            if (queryLength <= 5) {
                if (queryLength == 5) {

                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords5(qList.get(0), qList.get(1), qList.get(2),
                            qList.get(3), qList.get(4), page);
                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 5; i++) {

                            if (i == 3 || i == 4) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords4(qList.get(0), qList.get(1),
                                        qList.get(2), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }

                            }
                            if (i == 4) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords4(qList.get(0), qList.get(2),
                                        qList.get(3), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords4(qList.get(1), qList.get(2),
                                        qList.get(3), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                            }

                        }
                    }

                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 5; i++) {
                            if (i == 2 || i == 3 || i == 4) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(1),
                                        qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                            }
                            if (i == 3 || i == 4) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(2),
                                        qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }

                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(1), qList.get(2),
                                        qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                            }
                            if (i == 4) {

                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(3),
                                        qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(1), qList.get(3),
                                        qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                            }
                        }
                    }

                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 5; i++) {
                            if (i != 0) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(0), qList.get(i),
                                        page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                if (i != 1) {
                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(1),
                                            qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }
                                    if (i != 2) {
                                        tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(2),
                                                qList.get(i), page);
                                        if (!tutorialPageList.isEmpty()) {
                                            break;

                                        }
                                        if (i != 3) {
                                            tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(3),
                                                    qList.get(i), page);
                                            if (!tutorialPageList.isEmpty()) {
                                                break;

                                            }
                                        }

                                    }

                                }
                            }

                        }
                    }
                }

                if (queryLength == 4) {

                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords4(qList.get(0), qList.get(1), qList.get(2),
                            qList.get(3), page);

                    if (tutorialPageList.isEmpty()) {
                        for (int i = 2; i < 4; i++) {
                            if (i == 2 || i == 3) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(1),
                                        qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                if (i == 2) {

                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(1),
                                            qList.get(3), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }

                                }

                                if (i == 3) {
                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(0),
                                            qList.get(2), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }

                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(1),
                                            qList.get(2), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }

                                }

                            }

                        }
                    }

                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 4; i++) {
                            if (i != 0) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(0), qList.get(i),
                                        page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                if (i != 1) {
                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(1),
                                            qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }
                                    if (i != 2) {
                                        tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(2),
                                                qList.get(i), page);
                                        if (!tutorialPageList.isEmpty()) {
                                            break;

                                        }

                                    }

                                }
                            }

                        }
                    }

                }

                if (queryLength == 3) {

                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(1), qList.get(2),
                            page);
                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 3; i++) {
                            if (i != 0) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(0), qList.get(i),
                                        page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                if (i != 1) {
                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(1),
                                            qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }

                                }
                            }

                        }
                    }

                }

                if (queryLength == 2) {

                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords2(qList.get(0), qList.get(1), page);
                    if (tutorialPageList.isEmpty()) {
                        tutorialPageList = tutorialRepo.findByOutlineContainingIgnoreCase(qList.get(0), page);
                        if (tutorialPageList.isEmpty()) {
                            tutorialPageList = tutorialRepo.findByOutlineContainingIgnoreCase(qList.get(1), page);

                        }
                    }

                }

            }

            return tutorialPageList;
        }
    }

    @Override
    public Page<Tutorial> SearchOutlineByCombinationOfWordsWithConAssisgendTutorials(
            List<ContributorAssignedTutorial> con, String query, Pageable page) {
        Page<Tutorial> tutorialPageList = null;
        query = query.trim();
        List<String> qList = new ArrayList<String>(Arrays.asList(query.split(" ")));
        int queryLength = qList.size();
        if (queryLength > 5) {
            queryLength = 5;
        }

        tutorialPageList = tutorialRepo.findByOutlineByQueryPaginationconAssignedTutorialList(con, query, page);

        if (!tutorialPageList.isEmpty()) {
            return tutorialPageList;
        }

        else {

            if (queryLength <= 5) {
                if (queryLength == 5) {

                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords5conAssignedTutorialList(con, qList.get(0),
                            qList.get(1), qList.get(2), qList.get(3), qList.get(4), page);
                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 5; i++) {

                            if (i == 3 || i == 4) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords4conAssignedTutorialList(con,
                                        qList.get(0), qList.get(1), qList.get(2), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }

                            }
                            if (i == 4) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords4conAssignedTutorialList(con,
                                        qList.get(0), qList.get(2), qList.get(3), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords4conAssignedTutorialList(con,
                                        qList.get(1), qList.get(2), qList.get(3), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                            }

                        }
                    }

                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 5; i++) {
                            if (i == 2 || i == 3 || i == 4) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(con,
                                        qList.get(0), qList.get(1), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                            }
                            if (i == 3 || i == 4) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(con,
                                        qList.get(0), qList.get(2), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }

                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(con,
                                        qList.get(1), qList.get(2), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                            }
                            if (i == 4) {

                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(con,
                                        qList.get(0), qList.get(3), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(con,
                                        qList.get(1), qList.get(3), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                            }
                        }
                    }

                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 5; i++) {
                            if (i != 0) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords2conAssignedTutorialList(con,
                                        qList.get(0), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                if (i != 1) {
                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords2conAssignedTutorialList(
                                            con, qList.get(1), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }
                                    if (i != 2) {
                                        tutorialPageList = tutorialRepo
                                                .findByOutlineByQuerywords2conAssignedTutorialList(con, qList.get(2),
                                                        qList.get(i), page);
                                        if (!tutorialPageList.isEmpty()) {
                                            break;

                                        }
                                        if (i != 3) {
                                            tutorialPageList = tutorialRepo
                                                    .findByOutlineByQuerywords2conAssignedTutorialList(con,
                                                            qList.get(3), qList.get(i), page);
                                            if (!tutorialPageList.isEmpty()) {
                                                break;

                                            }
                                        }

                                    }

                                }
                            }

                        }
                    }
                }

                if (queryLength == 4) {

                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords4conAssignedTutorialList(con, qList.get(0),
                            qList.get(1), qList.get(2), qList.get(3), page);

                    if (tutorialPageList.isEmpty()) {
                        for (int i = 2; i < 4; i++) {
                            if (i == 2 || i == 3) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(con,
                                        qList.get(0), qList.get(1), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                if (i == 2) {

                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(
                                            con, qList.get(1), qList.get(3), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }

                                }

                                if (i == 3) {
                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(
                                            con, qList.get(0), qList.get(2), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }

                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(
                                            con, qList.get(1), qList.get(2), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }

                                }

                            }

                        }
                    }

                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 4; i++) {
                            if (i != 0) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords2conAssignedTutorialList(con,
                                        qList.get(0), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                if (i != 1) {
                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords2conAssignedTutorialList(
                                            con, qList.get(1), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }
                                    if (i != 2) {
                                        tutorialPageList = tutorialRepo
                                                .findByOutlineByQuerywords2conAssignedTutorialList(con, qList.get(2),
                                                        qList.get(i), page);
                                        if (!tutorialPageList.isEmpty()) {
                                            break;

                                        }

                                    }

                                }
                            }

                        }
                    }

                }

                if (queryLength == 3) {

                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords3conAssignedTutorialList(con, qList.get(0),
                            qList.get(1), qList.get(2), page);
                    if (tutorialPageList.isEmpty()) {
                        for (int i = 0; i < 3; i++) {
                            if (i != 0) {
                                tutorialPageList = tutorialRepo.findByOutlineByQuerywords2conAssignedTutorialList(con,
                                        qList.get(0), qList.get(i), page);
                                if (!tutorialPageList.isEmpty()) {
                                    break;

                                }
                                if (i != 1) {
                                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords2conAssignedTutorialList(
                                            con, qList.get(1), qList.get(i), page);
                                    if (!tutorialPageList.isEmpty()) {
                                        break;

                                    }

                                }
                            }

                        }
                    }

                }

                if (queryLength == 2) {

                    tutorialPageList = tutorialRepo.findByOutlineByQuerywords2conAssignedTutorialList(con, qList.get(0),
                            qList.get(1), page);
                    if (tutorialPageList.isEmpty()) {
                        tutorialPageList = tutorialRepo.findByOutlineByQueryPaginationconAssignedTutorialList(con,
                                qList.get(0), page);
                        if (tutorialPageList.isEmpty()) {
                            tutorialPageList = tutorialRepo.findByOutlineByQueryPaginationconAssignedTutorialList(con,
                                    qList.get(1), page);

                        }
                    }

                }

            }

            return tutorialPageList;
        }
    }

}