package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.PackageContainer;

public interface PackageContainerRepository extends JpaRepository<PackageContainer, Integer> {

    PackageContainer findByPackageId(int packageId);

    PackageContainer findByPackageName(String packageName);

}
