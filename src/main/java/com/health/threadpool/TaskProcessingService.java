package com.health.threadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.health.model.QueueManagement;
import com.health.repository.QueueManagementRepository;
import com.health.utility.CommonData;

@Service
public class TaskProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(TaskProcessingService.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private QueueManagementRepository queueRepo;

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String, Long> runningDocuments = new ConcurrentHashMap<>();

    public void intializeQueue() {
        List<QueueManagement> qmnts = queueRepo.findByStatusOrderByRequestTimeAsc(CommonData.STATUS_QUEUED);
        for (QueueManagement qmnt : qmnts) {
            logger.info("Pending:{}", qmnt);
            qmnt.setStatus(CommonData.STATUS_PENDING);
            qmnt.setQueueTime(0);
            queueRepo.save(qmnt);
        }

        qmnts = queueRepo.findByStatusOrderByRequestTimeAsc(CommonData.STATUS_PROCESSING);
        for (QueueManagement qmnt : qmnts) {
            logger.info("Pending:{}", qmnt);
            qmnt.setStatus(CommonData.STATUS_PENDING);
            qmnt.setQueueTime(0);
            queueRepo.save(qmnt);
        }

    }

    @Async
    public void queueProcessor() {

        logger.info("starting QueueProcessor thread");

        while (true) {
            if (Thread.interrupted()) {
                logger.info("Interrupted");
                break;
            }

            Map<String, Long> skippedDocuments = new HashMap<>();
            skippedDocuments.putAll(getRunningDocuments());
            int count = 0;

            List<QueueManagement> qmnts = queueRepo.findByStatusOrderByRequestTimeAsc(CommonData.STATUS_PENDING);
            if (qmnts == null) {
                try {
                    Thread.sleep(CommonData.NO_TASK_SLEEP_TIME);
                    continue;

                } catch (InterruptedException e) {

                }
            }

            for (QueueManagement qmnt : qmnts) {
                logger.info("Queueing:{}", qmnt);
                if (skippedDocuments.containsKey(qmnt.getDocumentId())) {

                    continue;
                }

                skippedDocuments.put(qmnt.getDocumentId(), System.currentTimeMillis());
                getRunningDocuments().put(qmnt.getDocumentId(), System.currentTimeMillis());
                qmnt.setStatus(CommonData.STATUS_QUEUED);
                qmnt.setQueueTime(System.currentTimeMillis());
                queueRepo.save(qmnt);

                applicationContext.getAutowireCapableBeanFactory().autowireBean(qmnt);
                taskExecutor.submit(qmnt);
                count = count + 1;

            }
            long sleepTime = count > 0 ? CommonData.TASK_SLEEP_TIME : CommonData.NO_TASK_SLEEP_TIME;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                logger.info("Interrupted");
                break;
            }
        }

    }

    public Map<String, Long> getRunningDocuments() {
        return runningDocuments;
    }

    public void stop() {
        logger.info("stopping QueueProcessor thread");
        taskExecutor.shutdown();
    }
}
