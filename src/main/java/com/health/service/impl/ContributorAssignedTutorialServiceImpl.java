package com.health.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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

/**
 * Default implementation of the
 * {@link com.health.service.ContributorAssignedTutorialService} interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class ContributorAssignedTutorialServiceImpl implements ContributorAssignedTutorialService {
    private static final Logger logger = LoggerFactory.getLogger(ContributorAssignedTutorialServiceImpl.class);

    @Autowired
    private ContributorAssignedTutorialRepository conRepo;

    /**
     * @see com.health.service.ContributorAssignedTutorialService#getNewId()
     */
    @Override
    public int getNewId() {
        // TODO Auto-generated method stub
        try {
            return conRepo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("New Id error in ContributorAssigned Tutorial Service Impl : {}", conRepo.getNewId(), e);
            return 1;

        }
    }

    /**
     * @see com.health.service.ContributorAssignedTutorialService#findByTopicCatLan(List,
     *      List)
     */
    @Override
    public List<ContributorAssignedTutorial> findByTopicCatLan(List<TopicCategoryMapping> topCat,
            List<UserRole> usrRole) {
        // Extract the languages from the UserRole list
        List<Language> languages = usrRole.stream().map(UserRole::getLanguage).collect(Collectors.toList());

        // Call the repository method with the lists of topic categories and languages
        List<ContributorAssignedTutorial> tutorials = conRepo.findByTopicCatLanList(topCat, languages);

        // Use a Set to store unique items and then convert back to a List
        return new ArrayList<>(new HashSet<>(tutorials));
    }

    /**
     * @see com.health.service.ContributorAssignedTutorialService#findAllByTopicCat(List)
     */
    @Override
    public List<ContributorAssignedTutorial> findAllByTopicCat(List<TopicCategoryMapping> topCat) {
        // TODO Auto-generated method stub
        return conRepo.findByTopicCat(topCat);
    }

    /**
     * @see com.health.service.ContributorAssignedTutorialService#findAllByTopicCatAndLan(List,
     *      Language)
     */
    @Override
    public List<ContributorAssignedTutorial> findAllByTopicCatAndLan(List<TopicCategoryMapping> topCat, Language lan) {
        // TODO Auto-generated method stub
        List<ContributorAssignedTutorial> localData = new ArrayList<ContributorAssignedTutorial>();

        for (TopicCategoryMapping temp : topCat) {

            localData.add(conRepo.findByTopicCatLan(temp, lan));

        }

        return localData;
    }

    @Override
    public List<ContributorAssignedTutorial> findAllByTopicCatAndLanViewPartwithCategoryTrue(
            List<TopicCategoryMapping> topCat, Language lan) {
        // TODO Auto-generated method stub
        List<ContributorAssignedTutorial> localData = new ArrayList<ContributorAssignedTutorial>();

        for (TopicCategoryMapping temp : topCat) {

            if (temp.getCat().isStatus()) {
                localData.add(conRepo.findByTopicCatLan(temp, lan));
            }

        }

        return localData;
    }

    /**
     * @see com.health.service.ContributorAssignedTutorialService#findByTopicCatAndLanViewPart(TopicCategoryMapping,
     *      Language)
     */
    @Override
    public ContributorAssignedTutorial findByTopicCatAndLanViewPart(TopicCategoryMapping topCat, Language lan) {
        // TODO Auto-generated method stub
        return conRepo.findByTopicCatLan(topCat, lan);
    }

    /**
     * @see com.health.service.ContributorAssignedTutorialService#findAllByTopicCat(List)
     */
    @Override
    public List<ContributorAssignedTutorial> findByTopicCat(TopicCategoryMapping topCat) {
        // TODO Auto-generated method stub
        return conRepo.findAllBytopicCatId(topCat);
    }

    /**
     * @see com.health.service.ContributorAssignedTutorialService#findAllByLan(Language)
     */
    @Override
    public List<ContributorAssignedTutorial> findAllByLan(Language lan) {
        // TODO Auto-generated method stub
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

    /**
     * @see com.health.service.ContributorAssignedTutorialService#findAll()
     */
    @Override
    public List<ContributorAssignedTutorial> findAll() {
        // TODO Auto-generated method stub
        return (List<ContributorAssignedTutorial>) conRepo.findAll();
    }

    /**
     * @see com.health.service.ContributorAssignedTutorialService#save(ContributorAssignedTutorial)
     */
    @Override
    public void save(ContributorAssignedTutorial con) {
        // TODO Auto-generated method stub
        conRepo.save(con);
    }

    /**
     * @see com.health.service.ContributorAssignedTutorialService#findById(int)
     */
    @Override

    public ContributorAssignedTutorial findById(int id) {
        // TODO Auto-generated method stub
        return conRepo.findById(id).get();
    }

    @Override
    public List<ContributorAssignedTutorial> findByTopicCatLanList(List<TopicCategoryMapping> tcmList,
            List<Language> lanList) {
        if (tcmList.isEmpty() || lanList.isEmpty()) {
            return Collections.emptyList();
        }

        return conRepo.findByTopicCatLanList(tcmList, lanList);
    }

}
