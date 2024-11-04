package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.VideoResource;
import com.health.model.Week;
import com.health.model.WeekTitleVideo;
import com.health.repository.WeekTitleVideoRepository;
import com.health.service.WeekTitleVideoService;

@Service
public class WeekTitleVideoServiceImpl implements WeekTitleVideoService {

    @Autowired
    private WeekTitleVideoRepository repo;

    @Override
    public WeekTitleVideo findByWeekTitleVideoId(int weekTitleVideoId) {

        return repo.findByWeekTitleVideoId(weekTitleVideoId);
    }

    @Override
    public List<WeekTitleVideo> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(WeekTitleVideo weekTitleVideo) {
        repo.saveAndFlush(weekTitleVideo);

    }

    @Override
    public void saveAll(List<WeekTitleVideo> weekTitleList) {
        repo.saveAll(weekTitleList);
        repo.flush();

    }

    @Override
    public WeekTitleVideo findByWeekVideoResourceAndLan(Week week, VideoResource videoResource, String langName) {
        return repo.findByWeekVideoResourceAndLan(week, videoResource, langName);
    }

}
