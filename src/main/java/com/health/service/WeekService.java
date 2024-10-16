package com.health.service;

import java.util.List;

import com.health.model.Week;

public interface WeekService {

    Week findByWeekId(int weekId);

    Week findByWeekName(String weekName);

    List<Week> findAll();

    void save(Week week);

}
