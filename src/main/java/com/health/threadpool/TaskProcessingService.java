package com.health.threadpool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.model.QueueManagement;
import com.health.model.Tutorial;
import com.health.repository.QueueManagementRepository;
import com.health.repository.TutorialRepository;
import com.health.service.TutorialService;
import com.health.utility.CommonData;

@Service
public class TaskProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(TaskProcessingService.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private QueueManagementRepository queueRepo;
    @Autowired
    private CommonData commonData;

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String, Long> runningDocuments = new ConcurrentHashMap<>();
    @Autowired
    private TutorialService tutService;

    @Autowired
    private Environment env;
    @Autowired
    private TutorialRepository tutRepo;

    public void createOutlineFile() {

        List<Tutorial> tutList = tutService.findByOutlinePathNull();
        List<Tutorial> newtutList = new ArrayList<>();
        if (tutList != null) {
            for (Tutorial tut : tutList) {

                String outline = tut.getOutline();
                try {
                    tut.saveOutline(env.getProperty("spring.applicationexternalPath.name"), outline);
                    newtutList.add(tut);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    logger.error("Exception: ", e);
                }

            }
            logger.info("Tutorial List Size :{}", tutList.size());
            logger.info(" New Tutorial List Size :{}", newtutList.size());
            tutRepo.saveAll(newtutList);
        }

    }

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
    public void deleteQueueByApiStatus() {
        logger.info("starting deleteQueueByApiStatus thread");

        while (true) {
            if (Thread.interrupted()) {
                logger.info("Interrupted");
                break;
            }

            List<QueueManagement> queueList = queueRepo.findByStatusOrderByRequestTimeAsc(CommonData.STATUS_DONE);
            StringBuilder documentSb = new StringBuilder();
            documentSb.append(commonData.elasticSearch_url);
            documentSb.append("/");
            documentSb.append("queueStatus");
            documentSb.append("/");
            String tempUrl = documentSb.toString();

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

                int count = 0;

                for (QueueManagement queue : queueList) {

                    Long respondId = queue.getResponseId();
                    if (respondId != null && respondId != 0) {

                        String api_url = tempUrl + respondId;
                        logger.info("API_URL:{}", api_url);

                        HttpUriRequest request = new HttpGet(api_url);
                        HttpResponse response = httpClient.execute(request);

                        int statusCode = response.getStatusLine().getStatusCode();
                        count += 1;

                        if (statusCode == 200 || statusCode == 201) {
                            String jsonResponse = EntityUtils.toString(response.getEntity());
                            ObjectMapper objectMapper = new ObjectMapper();
                            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                            System.out.println("jsonNode" + jsonNode);

                            JsonNode publishedArray = jsonNode.get("status");
                            if (publishedArray != null && publishedArray.asText().equals(CommonData.STATUS_DONE)) {
                                queueRepo.delete(queue);
                                System.out.println(
                                        "respondId: " + respondId + " publishedArray: " + publishedArray.asText());

                            }

                        } else {
                            logger.info("Status Code:{} API URl:{}", statusCode, api_url);

                        }

                    }
                }

                long sleepTime = count > 0 ? CommonData.TASK_SLEEP_TIME_FOR_DELETE
                        : CommonData.NO_TASK_SLEEP_TIME_FOR_DELETE;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    logger.info("Interrupted");
                    break;
                }

            }

            catch (IOException e) {

                logger.error("Exception: ", e);
            }
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
