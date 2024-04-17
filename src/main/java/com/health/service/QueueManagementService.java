package com.health.service;

import java.util.List;

import com.health.model.QueueManagement;

public interface QueueManagementService {

    List<QueueManagement> findByStatusOrderByRequestTimeAscWithNqueries(String status);
}
