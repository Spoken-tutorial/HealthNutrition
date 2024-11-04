package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.PackageContainer;
import com.health.model.PackageLanguage;

public interface PackageLanguageRepository extends JpaRepository<PackageLanguage, Integer> {

    PackageLanguage findBypackageLanId(int packageLanId);

    @Query("SELECT pc FROM PackageLanguage pc WHERE pc.packageContainer = :packageContainer AND pc.lan.langName = :langName")
    PackageLanguage findByPackageContainerAndLan(@Param("packageContainer") PackageContainer packageContainer,
            @Param("langName") String langName);

}
