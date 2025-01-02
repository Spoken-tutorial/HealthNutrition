
package com.health.service;

import com.health.model.HistoricalData;

public interface HistoricalDataService {

    HistoricalData findById(int id);

    void save(HistoricalData historicalData);

}
