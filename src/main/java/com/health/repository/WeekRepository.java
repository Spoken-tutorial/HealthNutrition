
package com.health.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.Week;

public interface WeekRepository extends JpaRepository<Week, Integer> {

    Week findByWeekName(String weekName);

    void deleteByWeekId(int weekId);

    @Override
    <S extends Week> S save(S entity);

    Optional<Week> findByWeekId(int weekId);

}
