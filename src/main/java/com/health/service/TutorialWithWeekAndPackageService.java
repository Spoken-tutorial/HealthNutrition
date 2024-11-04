package com.health.service;

import java.util.List;

import com.health.model.PackageLanguage;
import com.health.model.TutorialWithWeekAndPackage;
import com.health.model.WeekTitleVideo;

public interface TutorialWithWeekAndPackageService {

    TutorialWithWeekAndPackage findByPackageId(int id);

    List<TutorialWithWeekAndPackage> findAll();

    void saveAll(List<TutorialWithWeekAndPackage> tutorialList);

    TutorialWithWeekAndPackage findByPackageLanguageAndWeektitle(PackageLanguage packageLanguage, WeekTitleVideo weekTitleVideo);

    void save(TutorialWithWeekAndPackage twp);
}
