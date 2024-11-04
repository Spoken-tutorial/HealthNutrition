package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Week;
import com.health.repository.WeekRepository;
import com.health.service.WeekService;

@Service
public class WeekServiceImpl implements WeekService {

    @Autowired
    private WeekRepository repo;

    @Override
    public Week findByWeekId(int weekId) {

        return repo.findByWeekId(weekId);
    }

    @Override
    public List<Week> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(Week week) {

        repo.saveAndFlush(week);

    }

}
