package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.LogManegement;

public interface LogMangementRepository extends CrudRepository<LogManegement, Integer> {

    @Query("select max(logId) from LogManegement")
    int getNewId();

    @Query("from LogManegement l where l.userRole ='SUPER_USER'")
    public List<LogManegement> getLogsWithSuperUser();

}
