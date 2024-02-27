package com.health.threadpool;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.health.model.Brouchure;
import com.health.model.FilesofBrouchure;
import com.health.model.QueueManagement;
import com.health.model.ResearchPaper;
import com.health.model.Tutorial;
import com.health.model.Version;
import com.health.repository.QueueManagementRepository;
import com.health.repository.TutorialRepository;
import com.health.repository.VersionRepository;
import com.health.service.BrouchureService;
import com.health.service.FilesofBrouchureService;
import com.health.service.ResearchPaperService;
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
    private ResearchPaperService researchPaperService;

    @Autowired
    private BrouchureService broService;

    @Autowired
    private VersionRepository verRepository;

    @Autowired
    FilesofBrouchureService filesofBroService;

    @Autowired
    private TutorialRepository tutRepo;

    private Timestamp getCurrentTime() {

        Date date = new Date();
        long t = date.getTime();
        Timestamp st = new Timestamp(t);

        return st;
    }

    public Map<String, String> addDocument(String documentId, String documentType, String documentPath,
            String documentUrl, int rank, String view_url, int languageId, String language,
            Optional<Integer> categoryId, Optional<String> category, Optional<Integer> topicId, Optional<String> topic,
            Optional<String> outlinePath, String requestType) {

        Map<String, String> resultMap = new HashMap<>();

        logger.info(
                "RequestType:{} Language:{} View_URL: {} documentId:{} documentPath:{} documentType:{} outlinePath:{}",
                requestType, language, view_url, documentId, documentPath, documentType, outlinePath);

        if (documentPath == null) {

            resultMap.put(CommonData.STATUS, CommonData.STATUS_FAILED);
            resultMap.put(CommonData.REASON, "documentPath file does not exist");
            return resultMap;

        }

        if (outlinePath != null && !outlinePath.isPresent()) {

            resultMap.put(CommonData.STATUS, CommonData.STATUS_FAILED);
            resultMap.put(CommonData.REASON, "outline file does not exist");
            return resultMap;

        }

        QueueManagement queuemnt = new QueueManagement();

        if (outlinePath != null && outlinePath.isPresent())
            queuemnt.setOutlinePath(outlinePath.get());
        queuemnt.setRequestTime(getCurrentTime());
        queuemnt.setRequestType(requestType);
        queuemnt.setDocumentId(documentId);
        queuemnt.setDocumentType(documentType);
        queuemnt.setDocumentPath(documentPath);
        queuemnt.setDocumentUrl(documentUrl);
        queuemnt.setRank(rank);
        queuemnt.setViewUrl(view_url);
        queuemnt.setLanguageId(languageId);
        queuemnt.setStatus("pending");
        if (language != null)
            queuemnt.setLanguage(language);
        if (category != null && category.isPresent())
            queuemnt.setCategory(category.get());
        if (categoryId != null && categoryId.isPresent())
            queuemnt.setCategoryId(categoryId.get());
        if (topic != null && topic.isPresent())
            queuemnt.setTopic(topic.get());
        if (topicId != null && topicId.isPresent())
            queuemnt.setTopicId(topicId.get());

        queueRepo.save(queuemnt);

        resultMap.put(CommonData.QUEUE_ID, Long.toString(queuemnt.getQueueId()));
        resultMap.put(CommonData.STATUS, CommonData.SUCCESS);

        return resultMap;

    }

    public void addAllTuttorialsToQueue() {
        List<Tutorial> tutorials = tutRepo.findByTimeScriptNotNull();
        for (Tutorial tutorial : tutorials) {

            if (!tutorial.isAddedQueue()) {
                String documentId = CommonData.DOCUMENT_TYPE_TUTORIAL + tutorial.getTutorialId();
                String documentType = CommonData.DOCUMENT_TYPE_TUTORIAL;
                String documentPath = tutorial.getTimeScript();
                String documentUrl = "";
                if (tutorial.getConAssignedTutorial().getLan().getLanId() == 22)
                    documentUrl = "/TimeScript/" + tutorial.getTutorialId();
                else
                    documentUrl = "/OriginalScript/" + tutorial.getTutorialId();

                int rank = tutorial.getUserVisit() + 3 * tutorial.getResourceVisit();
                String view_url = null;
                int languageId = tutorial.getConAssignedTutorial().getLan().getLanId();
                String languag = tutorial.getConAssignedTutorial().getLan().getLangName();
                Optional<Integer> categoryId = Optional
                        .of(tutorial.getConAssignedTutorial().getTopicCatId().getCat().getCategoryId());
                Optional<String> category = Optional
                        .of(tutorial.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
                Optional<Integer> topicId = Optional
                        .of(tutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicId());
                Optional<String> topic = Optional
                        .of(tutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
                Optional<String> outlinePath = Optional.of(tutorial.getOutlinePath());
                String requestType = CommonData.ADD_DOCUMENT;

                Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl, rank,
                        view_url, languageId, languag, categoryId, category, topicId, topic, outlinePath, requestType);
                if (resultMap.containsValue(CommonData.SUCCESS))
                    tutorial.setAddedQueue(true);

                tutService.save(tutorial);

            }

        }
    }

    public void addUpdateDeleteResearchPaper(ResearchPaper researchPaper, String requestType) {

        String documentId = CommonData.DOCUMENT_TYPE_RESEARCHPAPER + researchPaper.getId();
        String documentType = CommonData.DOCUMENT_TYPE_RESEARCHPAPER;
        String documentPath = researchPaper.getResearchPaperPath();
        String documentUrl = "/ResearchPaper/ " + researchPaper.getId();
        int rank = 5 * researchPaper.getResearchPaperVisit();
        String view_url = "test";
        int languageId = 22;
        String languag = "English";

        if (requestType.equals(CommonData.ADD_DOCUMENT) && !researchPaper.isAddedQueue()) {

            Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl, rank,
                    view_url, languageId, languag, null, null, null, null, null, requestType);
            if (resultMap.containsValue(CommonData.SUCCESS))
                researchPaper.setAddedQueue(true);

        }

        else if (researchPaper.isAddedQueue()) {
            if (requestType.equals(CommonData.UPDATE_DOCUMENT) || requestType.equals(CommonData.UPDATE_DOCUMENT_RANK)
                    || requestType.equals(CommonData.DELETE_DOCUMENT)) {
                Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl, rank,
                        view_url, languageId, languag, null, null, null, null, null, requestType);

                if (requestType.equals(CommonData.DELETE_DOCUMENT)) {
                    if (resultMap.containsValue(CommonData.SUCCESS))
                        researchPaper.setAddedQueue(false);

                }
            }

        }

        researchPaperService.save(researchPaper);
    }

    public void addAllResearchPapertoQueue() {
        List<ResearchPaper> researchPapers = researchPaperService.findAll();
        for (ResearchPaper researchPaper : researchPapers) {
            if (!researchPaper.isAddedQueue()) {
                String documentId = CommonData.DOCUMENT_TYPE_RESEARCHPAPER + researchPaper.getId();
                String documentType = CommonData.DOCUMENT_TYPE_RESEARCHPAPER;
                String documentPath = researchPaper.getResearchPaperPath();
                String documentUrl = "/ResearchPaper/ " + researchPaper.getId();
                int rank = 5 * researchPaper.getResearchPaperVisit();
                String view_url = "test";
                int languageId = 22;
                String languag = "English";

                String requestType = CommonData.ADD_DOCUMENT;
                Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl, rank,
                        view_url, languageId, languag, null, null, null, null, null, requestType);
                if (resultMap.containsValue(CommonData.SUCCESS))
                    researchPaper.setAddedQueue(true);

            }

            researchPaperService.save(researchPaper);
        }

    }

    public void addAllBrochureToQueue() {
        List<Brouchure> broList = broService.findAll();
        for (Brouchure brochure : broList) {
            Version version = verRepository.findByBrouchureAndBroVersion(brochure, brochure.getPrimaryVersion());

            List<FilesofBrouchure> filesOfBroList = filesofBroService.findByVersion(version);
            for (FilesofBrouchure fileofBro : filesOfBroList) {

                if (!fileofBro.isAddedQueue()) {
                    String documentId = CommonData.DOCUMENT_TYPE_BROCHURE + brochure.getId() + "Version"
                            + version.getVerId() + "FilesId" + fileofBro.getBroFileId();
                    String documentType = CommonData.DOCUMENT_TYPE_BROCHURE;
                    String documentPath = fileofBro.getWebPath();
                    String documentUrl = "";

                    documentUrl = "/Brochure/" + brochure.getId();

                    int rank = 5 * brochure.getBrochureVisit();
                    String view_url = null;
                    int languageId = brochure.getLan().getLanId();
                    String languag = brochure.getLan().getLangName();
                    Optional<Integer> catId = Optional.empty();
                    Optional<String> category = Optional.empty();
                    Optional<Integer> topicId1 = Optional.empty();
                    Optional<String> topicName = Optional.empty();
                    if (brochure.getTopicCatId() != null) {
                        catId = Optional.of(brochure.getTopicCatId().getCat().getCategoryId());
                        category = Optional.of(brochure.getTopicCatId().getCat().getCatName());
                        topicId1 = Optional.of(brochure.getTopicCatId().getTopic().getTopicId());
                        topicName = Optional.of(brochure.getTopicCatId().getTopic().getTopicName());

                    }

                    String requestType = CommonData.ADD_DOCUMENT;

                    Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl,
                            rank, view_url, languageId, languag, catId, category, topicId1, topicName, null,
                            requestType);
                    if (resultMap.containsValue(CommonData.SUCCESS))
                        fileofBro.setAddedQueue(true);
                    filesofBroService.save(fileofBro);

                }

            }

        }
    }

    public void checkoutlinedata() {
        List<Tutorial> tutList = tutRepo.findByOutlinePathNotNull();
        int matchedCount = 0;
        int notMatchedCount = 0;
        List<Tutorial> notMatchedOutlinetutList = new ArrayList<>();
        for (Tutorial tut : tutList) {
            try {
                if (tut.getOutline() != null) {
                    if (tut.getOutline()
                            .equals(tut.loadOutline(env.getProperty("spring.applicationexternalPath.name")))) {
                        matchedCount += 1;
                    } else {
                        notMatchedOutlinetutList.add(tut);
                        notMatchedCount += 1;
                    }
                }

            } catch (IOException e) {

                logger.error("Exception: ", e);
            }
        }
        logger.info("MatchedCount:{}", matchedCount);
        logger.info("NotMatchedCount:{}", notMatchedCount);

        for (Tutorial temp : notMatchedOutlinetutList) {

            logger.info("Tutorial Id:{}", temp.getTutorialId());
            logger.info("Outline Data Size: #{}#", temp.getOutline().length());

            logger.info("Outline Data:{}", temp.getOutline());
            try {
                logger.info("Outline File  Data size:#{}#",
                        temp.loadOutline(env.getProperty("spring.applicationexternalPath.name")).length());
                logger.info("Outline File  Data:{}",
                        temp.loadOutline(env.getProperty("spring.applicationexternalPath.name")));
            } catch (IOException e) {

                logger.error("Exception: ", e);
            }

        }
    }

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
