package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.PackageLanguage;
import com.health.model.TutorialWithWeekAndPackage;
import com.health.model.WeekTitleVideo;
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
    public List<TutorialWithWeekAndPackage> findAll() {

        return repo.findAll();
    }

    @Override
    public void saveAll(List<TutorialWithWeekAndPackage> tutorialList) {
        repo.saveAll(tutorialList);
        repo.flush();

    }

    @Override
    public TutorialWithWeekAndPackage findByPackageLanguageAndWeektitle(PackageLanguage packageLanguage,
            WeekTitleVideo weekTitleVideo) {

        return repo.findByPackageLanguageAndWeekTitleVideo(packageLanguage, weekTitleVideo);
    }

    @Override
    public void save(TutorialWithWeekAndPackage twp) {
        repo.save(twp);

    }

    @Override
    public void delete(TutorialWithWeekAndPackage twp) {
        repo.delete(twp);

    }

    @Override
    public List<TutorialWithWeekAndPackage> findByWeekTitlesAndPackageLanguages(List<WeekTitleVideo> weekTitles,
            List<PackageLanguage> packageLanguages) {

        return repo.findByWeekTitlesAndPackageLanguages(weekTitles, packageLanguages);
    }

}
