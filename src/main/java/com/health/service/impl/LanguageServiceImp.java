package com.health.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Category;
import com.health.model.Language;
import com.health.model.Tutorial;
import com.health.repository.LangaugeRepository;
import com.health.repository.TutorialRepository;
import com.health.service.LanguageService;

/**
 * Default implementation of the {@link com.health.service.LanguageService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class LanguageServiceImp implements LanguageService {

    private static final Logger logger = LoggerFactory.getLogger(LanguageServiceImp.class);

    @Autowired
    private LangaugeRepository languageRepo;

    @Autowired
    private TutorialRepository tutRepo;

    /**
     * @see com.health.service.LanguageService#getAllLanguages()
     */
    @Override
    public List<Language> getAllLanguages() {
        // TODO Auto-generated method stub
        List<Language> lans = languageRepo.findAll();
        Collections.sort(lans);
        return lans;

    }

    /**
     * @see com.health.service.LanguageService#getnewLanId()
     */
    @Override
    public int getnewLanId() {
        // TODO Auto-generated method stub
        try {
            return languageRepo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" New Id error in  Language  Service Impl: {}", languageRepo.getNewId(), e);
            return 1;
        }
    }

    /**
     * @see com.health.service.LanguageService#getByLanName(String)
     */
    @Override
    public Language getByLanName(String langName) {
        // TODO Auto-generated method stub
        try {
            return languageRepo.findBylangName(langName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" Name error in Language Service Impl: {}", langName, e);
            return null;
        }

    }

    /**
     * @see com.health.service.LanguageService#getById(int)
     */
    @Override

    public Language getById(int lanId) {
        // TODO Auto-generated method stub
        try {
            Optional<Language> local = languageRepo.findById(lanId);

            return local.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" Id error in Language Service Impl: {}", lanId, e);
            return null;
        }
    }

    /**
     * @see com.health.service.LanguageService#save(Language)
     */
    @Override
    @CachePut(cacheNames = "languages", key = "#lan.lanId")
    public void save(Language lan) {
        // TODO Auto-generated method stub
        languageRepo.save(lan);

    }

    @Override
    @Cacheable(cacheNames = "languages")
    public List<Language> getLanguagesForCache() {

        List<Tutorial> tutorials = tutRepo.findAllByStatus(true);
        // List<Tutorial> tutorials = tutRepo.findAllByStatusTrue();

        Set<Language> langTemp = new HashSet<Language>();
        for (Tutorial temp : tutorials) {
            Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
            if (c.isStatus()) {
                langTemp.add(temp.getConAssignedTutorial().getLan());
            }

        }
        List<Language> lanTempSorted = new ArrayList<Language>(langTemp);
        Collections.sort(lanTempSorted);

        return lanTempSorted;
    }

}