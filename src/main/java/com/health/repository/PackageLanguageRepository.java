package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.Language;
import com.health.model.PackageContainer;
import com.health.model.PackageLanguage;

public interface PackageLanguageRepository extends JpaRepository<PackageLanguage, Integer> {

    PackageLanguage findBypackageLanId(int packageLanId);

    @Query("SELECT pc FROM PackageLanguage pc WHERE pc.packageContainer = :packageContainer AND pc.lan.langName = :langName")
    PackageLanguage findByPackageContainerAndLan(@Param("packageContainer") PackageContainer packageContainer,
            @Param("langName") String langName);

    @Query("SELECT pl.lan FROM PackageLanguage pl WHERE pl.packageContainer = :packageContainer")
    List<Language> findAllLanguagesByPackageContainer(@Param("packageContainer") PackageContainer packageContainer);

    @Query("SELECT DISTINCT pl.packageContainer FROM PackageLanguage pl")
    List<PackageContainer> findAllDistinctPackageContainers();

    @Query("SELECT DISTINCT pl.packageContainer FROM PackageLanguage pl where pl.status=:value")
    List<PackageContainer> findAllDistinctPackageContainersByStatus(boolean value);

    @Query("SELECT pl.lan FROM PackageLanguage pl WHERE pl.packageContainer = :packageContainer AND pl.status = true")
    List<Language> findAllEnabledLanguagesByPackageContainer(
            @Param("packageContainer") PackageContainer packageContainer);

    List<PackageLanguage> findAllByStatus(boolean value);

}
