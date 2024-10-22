package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.VideoResource;
import com.health.model.Week;
import com.health.model.WeekTitle;
import com.health.repository.WeekTitleRepository;
import com.health.service.WeekTitleService;

@Service
public class WeekTitleServiceImpl implements WeekTitleService {

    @Autowired
    private WeekTitleRepository repo;

    @Override
    public WeekTitle findByWeekTitleId(int weekTitleId) {

        return repo.findByWeekTitleId(weekTitleId);
    }

    @Override
    public List<WeekTitle> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(WeekTitle weekTitle) {
        repo.saveAndFlush(weekTitle);

    }

    @Override
    public void saveAll(List<WeekTitle> weekTitleList) {
        repo.saveAll(weekTitleList);
        repo.flush();

    }

    @Override
    public WeekTitle findByWeekVideoResourceAndLan(Week week, VideoResource videoResource, String langName) {
        return repo.findByWeekVideoResourceAndLan(week, videoResource, langName);
    }

}
