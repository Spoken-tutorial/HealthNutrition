package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.health.model.QueueManagement;

public interface QueueManagementRepository extends JpaRepository<QueueManagement, Long> {

    @Query("select max(queueId) from QueueManagement")
    Long getNewId();

    QueueManagement findByQueueId(Long queueId);

    List<QueueManagement> findByStatusOrderByRequestTimeAsc(String status);

}
