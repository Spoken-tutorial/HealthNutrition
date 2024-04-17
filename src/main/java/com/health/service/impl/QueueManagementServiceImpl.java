package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.health.model.QueueManagement;
import com.health.repository.QueueManagementRepository;
import com.health.service.QueueManagementService;

@Service
public class QueueManagementServiceImpl implements QueueManagementService {

    @Value("${spring.queryResult}")
    private int limitQuery;

    @Autowired
    private QueueManagementRepository repo;

    @Override
    public List<QueueManagement> findByStatusOrderByRequestTimeAscWithNqueries(String status) {
        List<QueueManagement> queueList = repo.findByStatusOrderByRequestTimeAsc(status);
        List<QueueManagement> resultList = new ArrayList<QueueManagement>();
        if (queueList.size() > limitQuery) {
            for (int i = 0; i < limitQuery; i++) {
                resultList.add(queueList.get(i));
            }
            return resultList;
        } else {
            return queueList;
        }
    }

}
