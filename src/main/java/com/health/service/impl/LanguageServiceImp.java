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

@Service
public class LanguageServiceImp implements LanguageService {

    private static final Logger logger = LoggerFactory.getLogger(LanguageServiceImp.class);

    @Autowired
    private LangaugeRepository languageRepo;

    @Autowired
    private TutorialRepository tutRepo;

    @Override
    public List<Language> getAllLanguages() {

        List<Language> lans = languageRepo.findAll();
        Collections.sort(lans);
        return lans;

    }

    @Override
    public int getnewLanId() {

        try {
            return languageRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in  Language  Service Impl: {}", languageRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public Language getByLanName(String langName) {

        try {
            return languageRepo.findBylangName(langName);
        } catch (Exception e) {

            logger.error(" Name error in Language Service Impl: {}", langName, e);
            return null;
        }

    }

    @Override

    public Language getById(int lanId) {

        try {
            Optional<Language> local = languageRepo.findById(lanId);

            return local.get();
        } catch (Exception e) {

            logger.error(" Id error in Language Service Impl: {}", lanId, e);
            return null;
        }
    }

    @Override
    @CachePut(cacheNames = "languages", key = "#lan.lanId")
    public void save(Language lan) {

        languageRepo.save(lan);

    }

    @Override
    @Cacheable(cacheNames = "languages")
    public List<Language> getLanguagesForCache() {

        List<Tutorial> tutorials = tutRepo.findAllByStatus(true);

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