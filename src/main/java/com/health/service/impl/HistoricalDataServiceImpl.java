package com.health.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.HistoricalData;
import com.health.repository.HistoricalDataRepository;
import com.health.service.HistoricalDataService;

@Service
public class HistoricalDataServiceImpl implements HistoricalDataService {

    @Autowired
    private HistoricalDataRepository repo;

    @Override
    public HistoricalData findById(int id) {
        return repo.findById(id);
    }

    @Override
    public void save(HistoricalData historicalData) {
        repo.save(historicalData);

    }

}
