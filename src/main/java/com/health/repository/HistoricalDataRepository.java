
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.HistoricalData;

public interface HistoricalDataRepository extends JpaRepository<HistoricalData, Integer> {

    HistoricalData findById(int id);

}