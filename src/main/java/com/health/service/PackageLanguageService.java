package com.health.service;

import java.util.List;

import com.health.model.PackageContainer;
import com.health.model.PackageLanguage;

public interface PackageLanguageService {

    PackageLanguage findBypackageLanId(int packageLanId);

    PackageLanguage findByPackageContainerAndLan(PackageContainer packageContainer, String langName);

    void save(PackageLanguage packageLanguage);

    List<PackageLanguage> findAll();

}
