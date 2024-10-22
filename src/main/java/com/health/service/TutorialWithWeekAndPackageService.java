package com.health.service;

import java.util.List;

import com.health.model.PackageLanguage;
import com.health.model.TutorialWithWeekAndPackage;
import com.health.model.WeekTitle;

public interface TutorialWithWeekAndPackageService {

    TutorialWithWeekAndPackage findByPackageId(int id);

    List<TutorialWithWeekAndPackage> findAll();

    void saveAll(List<TutorialWithWeekAndPackage> tutorialList);

    TutorialWithWeekAndPackage findByPackageLanguageAndWeektitle(PackageLanguage packageLanguage, WeekTitle weekTitle);

    void save(TutorialWithWeekAndPackage twp);
}
