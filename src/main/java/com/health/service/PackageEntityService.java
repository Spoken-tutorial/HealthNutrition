package com.health.service;

import java.util.List;

import com.health.model.PackageEntity;

public interface PackageEntityService {

    PackageEntity findByPackageName(String packageName);

    PackageEntity findByPackageId(int id);

    void save(PackageEntity packageEntity);

    List<PackageEntity> findAll();

}
