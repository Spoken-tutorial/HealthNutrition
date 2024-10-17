package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.Week;

public interface WeekRepository extends JpaRepository<Week, Integer> {

    Week findByWeekId(int weekId);

    Week findByWeekName(String weekName);

}
