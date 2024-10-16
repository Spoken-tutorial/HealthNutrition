package com.health.service;

import java.util.List;

import com.health.model.TutorialWithWeekAndPackage;

public interface TutorialWithWeekAndPackageService {

    TutorialWithWeekAndPackage findByPackageId(int id);

    TutorialWithWeekAndPackage findByTitle(String title);

    List<TutorialWithWeekAndPackage> findAll();

    void saveAll(List<TutorialWithWeekAndPackage> tutorialList);
}
