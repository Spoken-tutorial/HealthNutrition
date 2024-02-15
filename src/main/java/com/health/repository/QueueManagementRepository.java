package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.QueueManagement;

public interface QueueManagementRepository extends JpaRepository<QueueManagement, Long> {

    QueueManagement findByQueueId(Long queueId);

    List<QueueManagement> findByStatusOrderByRequestTimeAsc(String status);

}
