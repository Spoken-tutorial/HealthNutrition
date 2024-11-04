package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.PackageLanguage;
import com.health.model.TutorialWithWeekAndPackage;
import com.health.model.WeekTitleVideo;

public interface TutorialWithWeekAndPackageRepository extends JpaRepository<TutorialWithWeekAndPackage, Integer> {

    TutorialWithWeekAndPackage findById(int id);

    @Query("SELECT twp FROM TutorialWithWeekAndPackage twp WHERE twp.packageLanguage = :packageLanguage AND twp.weekTitleVideo = :weekTitleVideo")
    TutorialWithWeekAndPackage findByPackageLanguageAndWeekTitleVideo(
            @Param("packageLanguage") PackageLanguage packageLanguage,
            @Param("weekTitleVideo") WeekTitleVideo weekTitleVideo);

    @Query("SELECT t FROM TutorialWithWeekAndPackage t WHERE t.weekTitle IN :weekTitles AND t.packageLanguage IN :packageLanguages")
    List<TutorialWithWeekAndPackage> findByWeekTitlesAndPackageLanguages(
            @Param("weekTitles") List<WeekTitle> weekTitles,
            @Param("packageLanguages") List<PackageLanguage> packageLanguages);

}
