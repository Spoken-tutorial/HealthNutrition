package com.health.service;

import java.util.List;

import com.health.model.PackLanTutorialResource;
import com.health.model.PackageLanguage;
import com.health.model.Tutorial;

public interface PackLanTutorialResourceService {

    PackLanTutorialResource findByPackageLanguageAndTutorial(PackageLanguage packageLanguage, Tutorial tutorial);

    PackLanTutorialResource findById(int id);

    List<PackLanTutorialResource> findByPackageLanguage(PackageLanguage packageLanguage);

    void save(PackLanTutorialResource packLanTutorialResource);

    void saveAll(List<PackLanTutorialResource> packLanTutorialResourceList);

}
