package com.health.threadpool;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.health.model.QueueManagement;

@Service
public class TaskProcessingService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Async
    public void queueProcessor() {
        int count = 0;
        List<QueueManagement> queueList = Arrays.asList(
                new QueueManagement(1L, "docId1", "Tutorial", 1, "English", 18, "pending"),
                new QueueManagement(2L, "docId2", "Tutorial", 1, "English", 18, "pending"),
                new QueueManagement(1L, "docId3", "Tutorial", 1, "English", 18, "pending"),
                new QueueManagement(1L, "docId4", "Tutorial", 1, "English", 18, "pending"));

        for (QueueManagement queue : queueList) {

            applicationContext.getAutowireCapableBeanFactory().autowireBean(queue);
            taskExecutor.submit(queue);
            count++;

        }
        if (count > 1) {
            try {
                Thread.sleep(500000);
                ;
            } catch (InterruptedException e) {

            }

        }
    }

}
