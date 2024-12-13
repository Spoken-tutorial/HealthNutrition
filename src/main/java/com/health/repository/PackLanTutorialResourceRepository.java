package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.PackLanTutorialResource;
import com.health.model.PackageLanguage;
import com.health.model.Tutorial;

public interface PackLanTutorialResourceRepository extends JpaRepository<PackLanTutorialResource, Integer> {

    PackLanTutorialResource findById(int id);

    @Query("SELECT ptr FROM PackLanTutorialResource ptr WHERE ptr.packageLanguage = :packageLanguage AND ptr.tutorial = :tutorial")
    PackLanTutorialResource findByPackageLanguageAndTutorial(@Param("packageLanguage") PackageLanguage packageLanguage,
            @Param("tutorial") Tutorial tutorial);

    @Query("SELECT ptr FROM PackLanTutorialResource ptr WHERE ptr.packageLanguage = :packageLanguage")
    List<PackLanTutorialResource> findByPackageLanguage(@Param("packageLanguage") PackageLanguage packageLanguage);

    List<PackLanTutorialResource> findByTutorialAndPackageLanguage(Tutorial tutorial, PackageLanguage packageLanguage);

}