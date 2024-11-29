package com.health.service;

import java.util.List;

import com.health.model.Language;
import com.health.model.PackageContainer;
import com.health.model.PackageLanguage;

public interface PackageLanguageService {

    PackageLanguage findBypackageLanId(int packageLanId);

    PackageLanguage findByPackageContainerAndLan(PackageContainer packageContainer, String langName);

    void save(PackageLanguage packageLanguage);

    List<PackageLanguage> findAll();

    List<Language> findAllLanguagesByPackageContainer(PackageContainer packageContainer);

    List<PackageContainer> findAllDistinctPackageContainers();

    List<PackageContainer> findAllDistinctEnabledPackageContainers();

    List<PackageLanguage> findAllByStatus(boolean value);

}
