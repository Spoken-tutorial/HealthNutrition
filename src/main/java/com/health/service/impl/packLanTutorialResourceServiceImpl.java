package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.PackLanTutorialResource;
import com.health.model.PackageLanguage;
import com.health.model.Tutorial;
import com.health.repository.PackLanTutorialResourceRepository;
import com.health.service.PackLanTutorialResourceService;

@Service
public class packLanTutorialResourceServiceImpl implements PackLanTutorialResourceService {

    @Autowired
    private PackLanTutorialResourceRepository repo;

    @Override
    public PackLanTutorialResource findByPackageLanguageAndTutorial(PackageLanguage packageLanguage,
            Tutorial tutorial) {

        return repo.findByPackageLanguageAndTutorial(packageLanguage, tutorial);
    }

    @Override
    public PackLanTutorialResource findById(int id) {

        return repo.findById(id);
    }

    @Override
    public List<PackLanTutorialResource> findByPackageLanguage(PackageLanguage packageLanguage) {

        return repo.findByPackageLanguage(packageLanguage);
    }

    @Override
    public void save(PackLanTutorialResource packLanTutorialResource) {
        repo.save(packLanTutorialResource);

    }

    @Override
    public void saveAll(List<PackLanTutorialResource> packLanTutorialResourceList) {
        repo.saveAll(packLanTutorialResourceList);

    }

    @Override
    public List<PackLanTutorialResource> findResourcesByTutorialAndPackageLanguage(Tutorial tutorial,
            PackageLanguage packageLanguage) {
        return repo.findByTutorialAndPackageLanguage(tutorial, packageLanguage);
    }

    @Override
    public void delete(PackLanTutorialResource packLanTutorialResource) {
        repo.delete(packLanTutorialResource);

    }

}
