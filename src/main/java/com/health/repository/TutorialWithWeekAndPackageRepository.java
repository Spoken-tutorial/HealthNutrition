package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.PackageLanguage;
import com.health.model.TutorialWithWeekAndPackage;
import com.health.model.WeekTitle;

public interface TutorialWithWeekAndPackageRepository extends JpaRepository<TutorialWithWeekAndPackage, Integer> {

    TutorialWithWeekAndPackage findById(int id);

    @Query("SELECT twp FROM TutorialWithWeekAndPackage twp WHERE twp.packageLanguage = :packageLanguage AND twp.weekTitle = :weekTitle")
    TutorialWithWeekAndPackage findByPackageLanguageAndWeektitle(
            @Param("packageLanguage") PackageLanguage packageLanguage, @Param("weekTitle") WeekTitle weekTitle);

}
