package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.TutorialWithWeekAndPackage;

public interface TutorialWithWeekAndPackageRepository extends JpaRepository<TutorialWithWeekAndPackage, Integer> {

    TutorialWithWeekAndPackage findById(int id);

    TutorialWithWeekAndPackage findByTitle(String title);

}
