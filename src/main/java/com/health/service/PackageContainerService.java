package com.health.service;

import java.util.List;

import com.health.model.PackageContainer;

public interface PackageContainerService {

    PackageContainer findByPackageId(int packageId);

    PackageContainer findByPackageName(String packageName);

    List<PackageContainer> findAll();

    void save(PackageContainer packageContainer);

}
