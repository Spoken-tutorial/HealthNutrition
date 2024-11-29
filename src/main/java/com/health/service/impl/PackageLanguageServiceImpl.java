package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Language;
import com.health.model.PackageContainer;
import com.health.model.PackageLanguage;
import com.health.repository.PackageLanguageRepository;
import com.health.service.PackageLanguageService;

@Service
public class PackageLanguageServiceImpl implements PackageLanguageService {

    @Autowired
    private PackageLanguageRepository repo;

    @Override
    public PackageLanguage findBypackageLanId(int packageLanId) {
        return repo.findBypackageLanId(packageLanId);
    }

    @Override
    public PackageLanguage findByPackageContainerAndLan(PackageContainer packageContainer, String langName) {

        return repo.findByPackageContainerAndLan(packageContainer, langName);
    }

    @Override
    public void save(PackageLanguage packageLanguage) {
        repo.saveAndFlush(packageLanguage);

    }

    @Override
    public List<PackageLanguage> findAll() {

        return repo.findAll();
    }

    @Override
    public List<Language> findAllLanguagesByPackageContainer(PackageContainer packageContainer) {

        return repo.findAllLanguagesByPackageContainer(packageContainer);
    }

    @Override
    public List<PackageContainer> findAllDistinctPackageContainers() {

        return repo.findAllDistinctPackageContainers();
    }

    @Override
    public List<PackageLanguage> findAllByStatus(boolean value) {

        return repo.findAllByStatus(value);
    }

    @Override
    public List<PackageContainer> findAllDistinctEnabledPackageContainers() {
        List<PackageContainer> packageList = repo.findAllDistinctPackageContainersByStatus(true);

        return packageList;
    }

}
