package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.TutorialWithWeekAndPackage;
import com.health.repository.TutorialWithWeekAndPackageRepository;
import com.health.service.TutorialWithWeekAndPackageService;

@Service
public class TutorialWithWeekAndPackageServiceImpl implements TutorialWithWeekAndPackageService {

    @Autowired
    private TutorialWithWeekAndPackageRepository repo;

    @Override
    public TutorialWithWeekAndPackage findByPackageId(int id) {

        return repo.findById(id);
    }

    @Override
    public TutorialWithWeekAndPackage findByTitle(String title) {
        return repo.findByTitle(title);
    }

    @Override
    public List<TutorialWithWeekAndPackage> findAll() {

        return repo.findAll();
    }

    @Override
    public void saveAll(List<TutorialWithWeekAndPackage> tutorialList) {
        repo.saveAll(tutorialList);

    }

}
