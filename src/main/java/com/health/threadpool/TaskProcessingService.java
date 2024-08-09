package com.health.threadpool;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.model.Brouchure;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.FilesofBrouchure;
import com.health.model.QueueManagement;
import com.health.model.ResearchPaper;
import com.health.model.TopicCategoryMapping;
import com.health.model.Tutorial;
import com.health.model.Version;
import com.health.repository.QueueManagementRepository;
import com.health.repository.TutorialRepository;
import com.health.repository.VersionRepository;
import com.health.service.BrouchureService;
import com.health.service.FilesofBrouchureService;
import com.health.service.QueueManagementService;
import com.health.service.ResearchPaperService;
import com.health.service.TutorialService;
import com.health.utility.CommonData;
import com.health.utility.ServiceUtility;

@Service
public class TaskProcessingService {

    @Value("${scriptmanager_api}")
    private String scriptmanager_api;

    @Value("${scriptmanager_url}")
    private String scriptmanager_url;

    @Value("${scriptmanager_path}")
    private String scriptmanager_path;

    @Value("${scriptmanager_url_for_json}")
    private String scriptmanager_url_for_json;

    private static final Logger logger = LoggerFactory.getLogger(TaskProcessingService.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private QueueManagementService queueService;

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

    private String ScriptUrl(Tutorial tutorial) {

        ContributorAssignedTutorial conAssignedTutorial = tutorial.getConAssignedTutorial();
        TopicCategoryMapping topicCat = conAssignedTutorial.getTopicCatId();
        int catId = topicCat.getCat().getCategoryId();
        int lanId = conAssignedTutorial.getLan().getLanId();
        List<Integer> scriptVerList = ServiceUtility.getApiVersion(scriptmanager_api, catId, tutorial.getTutorialId(),
                lanId);
        String sm_url = "";

        StringBuilder sb = new StringBuilder();

        sb.append(scriptmanager_url);
        sb.append(scriptmanager_path);
        sb.append(String.valueOf(catId));
        sb.append("/");
        sb.append(String.valueOf(tutorial.getTutorialId()));
        sb.append("/");
        sb.append(String.valueOf(lanId));
        sb.append("/");
        sb.append(String.valueOf(topicCat.getTopic().getTopicName()));
        sb.append("/");
        if (scriptVerList != null && scriptVerList.size() > 0) {

            StringBuilder sb2 = new StringBuilder(sb);
            sb2.append(scriptVerList.get(0));
            sm_url = sb2.toString();
        }
        return sm_url;

    }

    public String createJsonSmUrl(Tutorial tutorial) {

        ContributorAssignedTutorial conAssignedTutorial = tutorial.getConAssignedTutorial();
        TopicCategoryMapping topicCat = conAssignedTutorial.getTopicCatId();
        int catId = topicCat.getCat().getCategoryId();
        int lanId = conAssignedTutorial.getLan().getLanId();
        int tutorialId = tutorial.getTutorialId();
        int version = 1;
        List<Integer> scriptVerList = ServiceUtility.getApiVersion(scriptmanager_api, catId, tutorial.getTutorialId(),
                lanId);

        if (scriptVerList != null && scriptVerList.size() > 0) {
            version = scriptVerList.get(0);
        }

        StringBuilder sb = new StringBuilder();

        sb.append(scriptmanager_url_for_json);
        sb.append(String.valueOf(catId));
        sb.append("/tutorial/");
        sb.append(String.valueOf(tutorialId));
        sb.append("/language/");
        sb.append(String.valueOf(lanId));
        sb.append("/scripts/");
        sb.append(String.valueOf(version));
        sb.append("/healthnutrition/");
        String sm_url_json = sb.toString();
        return sm_url_json;

    }

    private boolean doesFileExist(String filePath) {
        logger.info("filePath:{}", filePath);

        if (filePath.startsWith("https://")) {
            return true;
        }
        if (filePath.contains("..")
                || !filePath.startsWith(env.getProperty("spring.applicationexternalPath.baseName"))) {
            return false;
        }
        Path path = Paths.get(env.getProperty("spring.applicationexternalPath.name"), filePath);

        return Files.exists(path);
    }

    public Map<String, String> addDocument(String documentId, String documentType, String documentPath,
            String documentUrl, int rank, String view_url, int languageId, String language,
            Optional<Integer> categoryId, Optional<String> category, Optional<Integer> topicId, Optional<String> topic,
            Optional<String> outlinePath, String requestType, Optional<String> videoPath, Optional<String> title,
            Optional<String> description, Optional<String> thumbnailPath, Optional<Integer> orderValue) {

        logger.info(
                "addDocument documentId: {}, documentType:{}, documentPath:{}, documentUrl:{}, rank:{}, view_url:{}, languageId: {}, language:{}, "
                        + "categoryId:{}, category:{},topicId:{}, topic:{},outlinePath: {},requestType: {} , videoPath: {} , title: {}, description: {}, thumbnailPath: {}, orderValue: {}",
                documentId, documentType, documentPath, documentUrl, rank, view_url, languageId, language, categoryId,
                category, topicId, topic, outlinePath, requestType, videoPath, title, description, thumbnailPath,
                orderValue);

        Map<String, String> resultMap = new HashMap<>();

        if (documentPath == null
                && (requestType.equals(CommonData.ADD_DOCUMENT) || requestType.equals(CommonData.UPDATE_DOCUMENT))) {

            resultMap.put(CommonData.STATUS, CommonData.STATUS_FAILED);
            resultMap.put(CommonData.REASON, "documentPath is null");

            return resultMap;

        } else if (documentPath != null && !doesFileExist(documentPath)) {

            resultMap.put(CommonData.STATUS, CommonData.STATUS_FAILED);
            resultMap.put(CommonData.REASON, "documentPath file does not exist");
            logger.info("documentPath file does not exist");

            return resultMap;

        }

        if (outlinePath != null && outlinePath.isPresent() && !doesFileExist(outlinePath.get())) {

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
        queuemnt.setStatus(CommonData.STATUS_PENDING);
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

        if (videoPath != null && videoPath.isPresent())
            queuemnt.setVideoPath(videoPath.get());

        if (title != null && title.isPresent())
            queuemnt.setTitle(title.get());

        if (description != null && description.isPresent())
            queuemnt.setDescription(description.get());

        if (thumbnailPath != null && thumbnailPath.isPresent())
            queuemnt.setThumbnailPath(thumbnailPath.get());

        if (orderValue != null && orderValue.isPresent())
            queuemnt.setOrderValue(orderValue.get());

        queueRepo.save(queuemnt);

        resultMap.put(CommonData.QUEUE_ID, Long.toString(queuemnt.getQueueId()));
        resultMap.put(CommonData.STATUS, CommonData.SUCCESS);

        return resultMap;

    }

    public void addUpdateDeleteTutorial(Tutorial tutorial, String requestType) {

        String documentTypeforOriginalScript = CommonData.DOCUMENT_TYPE_TUTORIAL_ORIGINAL_SCRIPT;
        String documentTypeforTimeScript = CommonData.DOCUMENT_TYPE_TUTORIAL_TIME_SCRIPT;
        String documentPathforTimeScript = "";
        String documentUrlforTimeScript = "";
        String documentIdforTimeScript = "";
        String view_urlforTimeScript = "";
        String sm_url = ScriptUrl(tutorial);
        ContributorAssignedTutorial conAssignedTutorial = tutorial.getConAssignedTutorial();
        TopicCategoryMapping tcm = conAssignedTutorial.getTopicCatId();

        if (conAssignedTutorial.getLan().getLanId() == 22) {
            documentPathforTimeScript = tutorial.getTimeScript();
            documentUrlforTimeScript = "/TimeScript/" + tutorial.getTutorialId();
            documentIdforTimeScript = CommonData.DOCUMENT_ID_TUTORIAL_TIMESCRIPT + tutorial.getTutorialId();
            view_urlforTimeScript = tutorial.getTimeScript();
        }

        String documentUrlforOriginalScript = "/OriginalScript/" + tutorial.getTutorialId();
        String view_urlforOriginalScript = sm_url;
        String documentIdforOriginalScript = CommonData.DOCUMENT_ID_TUTORIAL_ORIGINAL_SCRIPT + tutorial.getTutorialId();
        String documentPathforOriginalScript = createJsonSmUrl(tutorial);

        int rank = tutorial.getUserVisit() + 3 * tutorial.getResourceVisit();
        logger.info("rank:{}", rank);
        int languageId = conAssignedTutorial.getLan().getLanId();
        String language = conAssignedTutorial.getLan().getLangName();
        TopicCategoryMapping topicCat = conAssignedTutorial.getTopicCatId();
        Optional<Integer> categoryId = Optional.of(topicCat.getCat().getCategoryId());
        Optional<String> category = Optional.of(topicCat.getCat().getCatName());
        Optional<Integer> topicId = Optional.of(topicCat.getTopic().getTopicId());
        Optional<String> topic = Optional.of(topicCat.getTopic().getTopicName());
        Optional<String> videoPath = Optional.of(tutorial.getVideo());
        Optional<String> title = Optional.of("");
        Optional<String> description = Optional.of("");
        Optional<String> thumbnailPath = Optional.of("");
        Optional<String> videoPathEmpty = Optional.of("");
        Optional<Integer> orderValue = Optional.of(tcm.getOrder());
        Optional<String> outlinePath = null;
        if (tutorial.getOutlinePath() != null) {
            outlinePath = Optional.of(tutorial.getOutlinePath());
        }

        if (requestType.equals(CommonData.ADD_DOCUMENT) && !tutorial.isAddedQueue() && !sm_url.isEmpty()) {

            if (languageId == 22) {

                Map<String, String> resultMap1 = addDocument(documentIdforTimeScript, documentTypeforTimeScript,
                        documentPathforTimeScript, documentUrlforTimeScript, rank, view_urlforTimeScript, languageId,
                        language, categoryId, category, topicId, topic, null, requestType, videoPathEmpty, title,
                        description, thumbnailPath, orderValue);

                Map<String, String> resultMap2 = addDocument(documentIdforOriginalScript, documentTypeforOriginalScript,
                        documentPathforOriginalScript, documentUrlforOriginalScript, rank, view_urlforOriginalScript,
                        languageId, language, categoryId, category, topicId, topic, outlinePath, requestType, videoPath,
                        title, description, thumbnailPath, orderValue);

                if (resultMap1.containsValue(CommonData.SUCCESS) && resultMap2.containsValue(CommonData.SUCCESS))
                    tutorial.setAddedQueue(true);
            } else {

                Map<String, String> resultMap2 = addDocument(documentIdforOriginalScript, documentTypeforOriginalScript,
                        documentPathforOriginalScript, documentUrlforOriginalScript, rank, view_urlforOriginalScript,
                        languageId, language, categoryId, category, topicId, topic, outlinePath, requestType, videoPath,
                        title, description, thumbnailPath, orderValue);

                if (resultMap2.containsValue(CommonData.SUCCESS))
                    tutorial.setAddedQueue(true);
            }

        }

        else if (tutorial.isAddedQueue()) {
            if (requestType.equals(CommonData.UPDATE_DOCUMENT) || requestType.equals(CommonData.UPDATE_DOCUMENT_RANK)
                    || requestType.equals(CommonData.DELETE_DOCUMENT)) {

                if (languageId == 22) {

                    Map<String, String> resultMap1 = addDocument(documentIdforTimeScript, documentTypeforTimeScript,
                            documentPathforTimeScript, documentUrlforTimeScript, rank, view_urlforTimeScript,
                            languageId, language, categoryId, category, topicId, topic, null, requestType, null, null,
                            null, thumbnailPath, orderValue);

                    Map<String, String> resultMap2 = addDocument(documentIdforOriginalScript,
                            documentTypeforOriginalScript, documentPathforOriginalScript, documentUrlforOriginalScript,
                            rank, view_urlforOriginalScript, languageId, language, categoryId, category, topicId, topic,
                            outlinePath, requestType, videoPath, null, null, thumbnailPath, orderValue);

                    if (requestType.equals(CommonData.DELETE_DOCUMENT)) {
                        if (resultMap1.containsValue(CommonData.SUCCESS)
                                && resultMap2.containsValue(CommonData.SUCCESS))
                            tutorial.setAddedQueue(false);
                    }

                } else {

                    Map<String, String> resultMap2 = addDocument(documentIdforOriginalScript,
                            documentTypeforOriginalScript, documentPathforOriginalScript, documentUrlforOriginalScript,
                            rank, view_urlforOriginalScript, languageId, language, categoryId, category, topicId, topic,
                            outlinePath, requestType, videoPath, null, null, thumbnailPath, orderValue);

                    if (requestType.equals(CommonData.DELETE_DOCUMENT)) {
                        if (resultMap2.containsValue(CommonData.SUCCESS))
                            tutorial.setAddedQueue(false);

                    }
                }

            }

        }

        tutService.save(tutorial);
    }

    public void addUpdateDeleteResearchPaper(ResearchPaper researchPaper, String requestType) {

        String documentId = CommonData.DOCUMENT_ID_RESEARCHPAPER + researchPaper.getId();
        String documentType = CommonData.DOCUMENT_TYPE_RESEARCHPAPER;
        String documentPath = researchPaper.getResearchPaperPath();
        String documentUrl = "/ResearchPaper/ " + researchPaper.getId();
        int rank = 5 * researchPaper.getResearchPaperVisit();
        String view_url = researchPaper.getResearchPaperPath();

        Optional<String> videoPath = Optional.of("");
        Optional<Integer> categoryId = Optional.of(0);
        Optional<Integer> topicId = Optional.of(0);
        Optional<String> category = Optional.of("");
        Optional<String> topic = Optional.of("");
        Optional<String> thumbnailPath = Optional.of("");
        Optional<String> title = Optional.of("");
        Optional<String> description = Optional.of("");

        if (researchPaper.getThumbnailPath() != null) {
            thumbnailPath = Optional.of(researchPaper.getThumbnailPath());
        }

        if (researchPaper.getTitle() != null) {
            title = Optional.of(researchPaper.getTitle());
        }

        if (researchPaper.getDescription() != null) {
            description = Optional.of(researchPaper.getDescription());
        }

        int languageId = 22;
        String language = "English";

        if (requestType.equals(CommonData.ADD_DOCUMENT) && !researchPaper.isAddedQueue()) {

            Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl, rank,
                    view_url, languageId, language, categoryId, category, topicId, topic, null, requestType, videoPath,
                    title, description, thumbnailPath, null);
            if (resultMap.containsValue(CommonData.SUCCESS))
                researchPaper.setAddedQueue(true);

        }

        else if (researchPaper.isAddedQueue()) {
            if (requestType.equals(CommonData.UPDATE_DOCUMENT) || requestType.equals(CommonData.UPDATE_DOCUMENT_RANK)
                    || requestType.equals(CommonData.DELETE_DOCUMENT)) {
                logger.info("Update Research Paper");
                Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl, rank,
                        view_url, languageId, language, null, null, null, null, null, requestType, null, title,
                        description, thumbnailPath, null);

                if (requestType.equals(CommonData.DELETE_DOCUMENT)) {
                    if (resultMap.containsValue(CommonData.SUCCESS))
                        researchPaper.setAddedQueue(false);

                }
            }

        }

        researchPaperService.save(researchPaper);
    }

    public void addUpdateDeleteBrochure(Brouchure brochure, String requestType) {

        Version version = verRepository.findByBrouchureAndBroVersion(brochure, brochure.getPrimaryVersion());
        List<FilesofBrouchure> filesOfBroList = filesofBroService.findByVersion(version);

        int rank = 5 * brochure.getBrochureVisit();

        int languageId = brochure.getLan().getLanId();
        String language = brochure.getLan().getLangName();

        Optional<String> title = Optional.of(brochure.getTitle());
        Optional<String> description = Optional.of("");
        Optional<String> videoPath = Optional.of("");
        Optional<String> thumbnailPath = Optional.of("");

        Optional<Integer> catId = Optional.empty();
        Optional<String> category = Optional.empty();
        Optional<Integer> topicId1 = Optional.empty();
        Optional<String> topicName = Optional.empty();

        TopicCategoryMapping topicCat = brochure.getTopicCatId();
        if (topicCat != null) {
            catId = Optional.of(topicCat.getCat().getCategoryId());
            category = Optional.of(topicCat.getCat().getCatName());
            topicId1 = Optional.of(topicCat.getTopic().getTopicId());
            topicName = Optional.of(topicCat.getTopic().getTopicName());

        }

        String documentType = CommonData.DOCUMENT_TYPE_BROCHURE;

        for (FilesofBrouchure fileofBro : filesOfBroList) {

            String documentId = CommonData.DOCUMENT_ID_BROCHURE + brochure.getId() + "."
                    + CommonData.DOCUMENT_ID_VERSION + version.getVerId() + "."
                    + CommonData.DOCUMENT_ID_FILES_OF_BROCHURE + fileofBro.getBroFileId();

            String documentPath = fileofBro.getWebPath();
            String view_url = fileofBro.getWebPath();
            String documentUrl = "/Brochure/" + fileofBro.getBroFileId();
            if (fileofBro.getThumbnailPath() != null) {
                thumbnailPath = Optional.of(fileofBro.getThumbnailPath());
            }

            if (requestType.equals(CommonData.ADD_DOCUMENT) && !fileofBro.isAddedQueue()) {

                Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl, rank,
                        view_url, languageId, language, catId, category, topicId1, topicName, null, requestType,
                        videoPath, title, description, thumbnailPath, null);
                if (resultMap.containsValue(CommonData.SUCCESS))
                    fileofBro.setAddedQueue(true);

            }

            else if (fileofBro.isAddedQueue()) {
                if (requestType.equals(CommonData.UPDATE_DOCUMENT)
                        || requestType.equals(CommonData.UPDATE_DOCUMENT_RANK)
                        || requestType.equals(CommonData.DELETE_DOCUMENT)) {
                    Map<String, String> resultMap = addDocument(documentId, documentType, documentPath, documentUrl,
                            rank, view_url, languageId, language, catId, category, topicId1, topicName, null,
                            requestType, null, title, null, thumbnailPath, null);

                    if (requestType.equals(CommonData.DELETE_DOCUMENT)) {
                        if (resultMap.containsValue(CommonData.SUCCESS))
                            fileofBro.setAddedQueue(false);

                    }
                }

            }

            filesofBroService.save(fileofBro);

        }

    }

    public void addAllTuttorialsToQueue() {

        List<Tutorial> tutorialList = tutRepo.findTutorialsWithStatusTrueAndAddedQueueFalseAndCatregoryEnabled();

        logger.info("tutorial size:{}", tutorialList.size());
        List<Tutorial> newtTutorials = new ArrayList<>();
        for (Tutorial tutorial : tutorialList) {
            try {
                addUpdateDeleteTutorial(tutorial, CommonData.ADD_DOCUMENT);
                newtTutorials.add(tutorial);
            } catch (Exception e) {
                logger.error("Exception of addAllTuttorialsToQueue: {}", tutorial, e);

            }

        }
        tutRepo.saveAll(newtTutorials);
    }

    public void addAllResearchPapertoQueue() {

        List<ResearchPaper> researchPapers = researchPaperService.findByShowOnHomepageIsTrueAndAddedQueueIsFalse();
        for (ResearchPaper researchPaper : researchPapers) {
            try {
                addUpdateDeleteResearchPaper(researchPaper, CommonData.ADD_DOCUMENT);
            } catch (Exception e) {
                logger.error("Exception of addAllResearchPapertoQueue: {}", researchPaper, e);
            }

        }

    }

    public void updateAllResearchPapertoQueue() {

        List<ResearchPaper> researchPapers = researchPaperService.findByShowOnHomepageIsTrueAndAddedQueueIsTrue();
        for (ResearchPaper researchPaper : researchPapers) {
            try {
                addUpdateDeleteResearchPaper(researchPaper, CommonData.UPDATE_DOCUMENT);
            } catch (Exception e) {
                logger.error("Exception of addAllResearchPapertoQueue: {}", researchPaper, e);
            }

        }

    }

    public void addAllBrochureToQueue() {

        List<Brouchure> broList = broService.findAllBrouchuresForCache();
        for (Brouchure brochure : broList) {
            try {
                addUpdateDeleteBrochure(brochure, CommonData.ADD_DOCUMENT);
            } catch (Exception e) {
                logger.error("Exception of addAllBrochureToQueue :{}", brochure, e);
            }

        }
    }

    public void updateAllBrochureToQueue() {

        List<Brouchure> broList = broService.findAllBrouchuresForCache();
        for (Brouchure brochure : broList) {
            try {
                addUpdateDeleteBrochure(brochure, CommonData.UPDATE_DOCUMENT);
            } catch (Exception e) {
                logger.error("Exception of addAllBrochureToQueue :{}", brochure, e);
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

                    logger.error("Exception: {}", tut, e);
                }

            }
            logger.info("Size of tutorials to create outline file :{}", tutList.size());
            logger.info(" Size of new Tutorials whose outline file are created :{}", newtutList.size());
            tutRepo.saveAll(newtutList);
        }

    }

    public void intializeQueue() {

        List<QueueManagement> qmnts = queueRepo.findByStatusOrderByRequestTimeAsc(CommonData.STATUS_QUEUED);
        for (QueueManagement qmnt : qmnts) {
            try {
                MDC.put("queueId", '@' + Long.toString(qmnt.getQueueId()));
                logger.info("Pending:{}", qmnt);
                qmnt.setStatus(CommonData.STATUS_PENDING);
                qmnt.setQueueTime(0);
                queueRepo.save(qmnt);
                logger.info("{}", qmnt.getStatusLog());
            } catch (Exception e) {

                logger.error("Exception of intializeQueue1: {}", qmnt, e);
            }
            logger.info("{}", qmnt.getStatusLog());
            MDC.remove("queueId");

        }

        qmnts = queueRepo.findByStatusOrderByRequestTimeAsc(CommonData.STATUS_PROCESSING);
        for (QueueManagement qmnt : qmnts) {
            try {
                MDC.put("queueId", '@' + Long.toString(qmnt.getQueueId()));
                logger.info("Pending:{}", qmnt);
                qmnt.setStatus(CommonData.STATUS_PENDING);
                qmnt.setQueueTime(0);
                queueRepo.save(qmnt);
                logger.info("{}", qmnt.getStatusLog());
            } catch (Exception e) {
                logger.error("Exception of intializeQueue2: {}", qmnt, e);
            }
            logger.info("{}", qmnt.getStatusLog());
            MDC.remove("queueId");

        }

    }

    public boolean isURLWorking(String url) {

        boolean flag = false;

        try {

            URL url1 = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                flag = true;
                logger.info("Connection Request successful");
            } else {

                logger.info("Request failed with status code:{} ", responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {

            logger.error("Request failed due to I/O error: " + e);
        }
        return flag;

    }

    @Async
    public void deleteQueueByApiStatus() {

        if (isURLWorking(commonData.elasticSearch_url)) {

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
                        MDC.put("queueId",
                                '@' + Long.toString(queue.getQueueId()) + '#' + Long.toString(queue.getResponseId()));
                        try {
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
                                    logger.info("jsonNode:{}", jsonNode);

                                    JsonNode publishedArray = jsonNode.get("status");
                                    if (publishedArray != null
                                            && publishedArray.asText().equals(CommonData.STATUS_DONE)) {
                                        queueRepo.delete(queue);
                                        logger.info("respondId: {}  publishedArray: {}", respondId,
                                                publishedArray.asText());

                                    }

                                } else {
                                    logger.info("Status Code:{} API URl:{}", statusCode, api_url);

                                }

                            }

                        } catch (Exception e) {
                            logger.error("Exception of deleteQueueByApiStatus :{}", queue, e);
                            MDC.remove("queueId");
                            continue;
                        }

                        MDC.remove("queueId");

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

    }

    @Async
    public void queueProcessor() {

        if (isURLWorking(commonData.elasticSearch_url)) {

            logger.info("starting QueueProcessor thread");

            while (true) {
                if (Thread.interrupted()) {
                    logger.info("Interrupted");
                    break;
                }

                Map<String, Long> skippedDocuments = new HashMap<>();
                skippedDocuments.putAll(getRunningDocuments());
                int count = 0;

                List<QueueManagement> qmnts = queueService
                        .findByStatusOrderByRequestTimeAscWithNqueries(CommonData.STATUS_PENDING);
                if (qmnts == null) {
                    try {
                        Thread.sleep(CommonData.NO_TASK_SLEEP_TIME);
                        continue;

                    } catch (InterruptedException e) {
                        logger.info("Interrupted");
                        break;
                    }
                }

                for (QueueManagement qmnt : qmnts) {
                    MDC.put("queueId", '@' + Long.toString(qmnt.getQueueId()));
                    try {
                        logger.info("Queueing:{}", qmnt);
                        if (skippedDocuments.containsKey(qmnt.getDocumentId())) {
                            logger.info("skipDocument contains the DocumentID: {}", qmnt.getDocumentId());
                            MDC.remove("queueId");
                            continue;
                        }

                        skippedDocuments.put(qmnt.getDocumentId(), System.currentTimeMillis());
                        getRunningDocuments().put(qmnt.getDocumentId(), System.currentTimeMillis());
                        qmnt.setStatus(CommonData.STATUS_QUEUED);
                        qmnt.setQueueTime(System.currentTimeMillis());

                        applicationContext.getAutowireCapableBeanFactory().autowireBean(qmnt);
                        taskExecutor.submit(qmnt);
                        queueRepo.save(qmnt);
                        logger.info("{}", qmnt.getStatusLog());

                        count = count + 1;

                    } catch (Exception e) {
                        logger.error("Exception of queueProcessor {}", qmnt, e);
                        MDC.remove("queueId");
                        continue;
                    }

                    MDC.remove("queueId");
                }
                long sleepTime = count > 0 ? CommonData.TASK_SLEEP_TIME : CommonData.NO_TASK_SLEEP_TIME;
                if (count > 0)
                    logger.info("Task_SLEEP_TIME: " + CommonData.TASK_SLEEP_TIME);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    logger.info("Interrupted");
                    break;
                }
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
