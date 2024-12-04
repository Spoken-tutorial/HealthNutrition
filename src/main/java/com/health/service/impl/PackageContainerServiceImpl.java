package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.PackageContainer;
import com.health.repository.PackageContainerRepository;
import com.health.service.PackageContainerService;

@Service
public class PackageContainerServiceImpl implements PackageContainerService {

    @Autowired
    private PackageContainerRepository repo;

    @Override
    public PackageContainer findByPackageId(int packageId) {

        return repo.findByPackageId(packageId);
    }

    @Override
    public PackageContainer findByPackageName(String packageName) {

        return repo.findByPackageName(packageName);
    }

    @Override
    public List<PackageContainer> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(PackageContainer packageContainer) {
        repo.saveAndFlush(packageContainer);

    }

    @Override
    public List<PackageContainer> findAllByStatus(boolean value) {

        return repo.findAllByStatus(value);
    }

}
