package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.domain.security.UserRole;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Language;
import com.health.model.TopicCategoryMapping;
import com.health.repository.ContributorAssignedTutorialRepository;
import com.health.service.ContributorAssignedTutorialService;

@Service
public class ContributorAssignedTutorialServiceImpl implements ContributorAssignedTutorialService {
    private static final Logger logger = LoggerFactory.getLogger(ContributorAssignedTutorialServiceImpl.class);

    @Autowired
    private ContributorAssignedTutorialRepository conRepo;

    @Override
    public int getNewId() {

        try {
            return conRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in ContributorAssigned Tutorial Service Impl : {}", conRepo.getNewId(), e);
            return 1;

        }
    }

    @Override
    public List<ContributorAssignedTutorial> findByTopicCatLan(List<TopicCategoryMapping> topCat,
            List<UserRole> usrRole) {

        List<ContributorAssignedTutorial> localData = new ArrayList<ContributorAssignedTutorial>();

        for (TopicCategoryMapping temp : topCat) {
            for (UserRole xlan : usrRole) {
                localData.add(conRepo.findByTopicCatLan(temp, xlan.getLanguage()));
            }

        }

        return localData;
    }

    @Override
    public List<ContributorAssignedTutorial> findAllByTopicCat(List<TopicCategoryMapping> topCat) {

        return conRepo.findByTopicCat(topCat);
    }

    @Override
    public List<ContributorAssignedTutorial> findAllByTopicCatAndLan(List<TopicCategoryMapping> topCat, Language lan) {

        List<ContributorAssignedTutorial> localData = new ArrayList<ContributorAssignedTutorial>();

        for (TopicCategoryMapping temp : topCat) {

            localData.add(conRepo.findByTopicCatLan(temp, lan));

        }

        return localData;
    }

    @Override
    public List<ContributorAssignedTutorial> findAllByTopicCatAndLanViewPartwithCategoryTrue(
            List<TopicCategoryMapping> topCat, Language lan) {

        List<ContributorAssignedTutorial> localData = new ArrayList<ContributorAssignedTutorial>();

        for (TopicCategoryMapping temp : topCat) {

            if (temp.getCat().isStatus()) {
                localData.add(conRepo.findByTopicCatLan(temp, lan));
            }

        }

        return localData;
    }

    @Override
    public ContributorAssignedTutorial findByTopicCatAndLanViewPart(TopicCategoryMapping topCat, Language lan) {

        return conRepo.findByTopicCatLan(topCat, lan);
    }

    @Override
    public List<ContributorAssignedTutorial> findByTopicCat(TopicCategoryMapping topCat) {

        return conRepo.findAllBytopicCatId(topCat);
    }

    @Override
    public List<ContributorAssignedTutorial> findAllByLan(Language lan) {

        return conRepo.findAllBylan(lan);
    }

    @Override
    public List<ContributorAssignedTutorial> findAllByLanWithcategoryTrue(Language lan) {
        List<ContributorAssignedTutorial> list1 = new ArrayList<>();
        List<ContributorAssignedTutorial> list2 = conRepo.findAllBylan(lan);
        for (ContributorAssignedTutorial temp : list2) {
            if (temp.getTopicCatId().getCat().isStatus()) {
                list1.add(temp);
            }
        }
        return list1;
    }

    @Override
    public List<ContributorAssignedTutorial> findAll() {

        return (List<ContributorAssignedTutorial>) conRepo.findAll();
    }

    @Override
    public void save(ContributorAssignedTutorial con) {

        conRepo.save(con);
    }

    @Override

    public ContributorAssignedTutorial findById(int id) {

        return conRepo.findById(id).get();
    }

}
