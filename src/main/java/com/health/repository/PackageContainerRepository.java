package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.PackageContainer;

public interface PackageContainerRepository extends JpaRepository<PackageContainer, Integer> {

    PackageContainer findByPackageId(int packageId);

    PackageContainer findByPackageName(String packageName);

    List<PackageContainer> findAllByStatus(boolean value);

}
