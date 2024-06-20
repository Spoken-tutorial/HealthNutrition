package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.PackageEntity;
import com.health.repository.PackageEntityReopository;
import com.health.service.PackageEntityService;

@Service
public class PackageEntityServiceImpl implements PackageEntityService {

    @Autowired
    private PackageEntityReopository repo;

    @Override
    public PackageEntity findByPackageName(String packageName) {
        return repo.findByPackageName(packageName);
    }

    @Override
    public PackageEntity findByPackageId(int id) {

        return repo.findByPackageId(id);
    }

    @Override
    public void save(PackageEntity packageEntity) {
        repo.save(packageEntity);

    }

    @Override
    public List<PackageEntity> findAll() {
        return repo.findAll();

    }

}
