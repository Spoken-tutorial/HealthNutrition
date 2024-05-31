package com.health.service;

import java.util.List;

import com.health.model.LogManegement;

public interface LogMangementService {

    int getNewId();

    void save(LogManegement log);

    List<LogManegement> getLogsWithSuperUser();
}
