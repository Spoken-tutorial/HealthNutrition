package com.health.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.repository.QueueManagementRepository;
import com.health.threadpool.TaskProcessingService;
import com.health.utility.CommonData;

@Entity
public class QueueManagement implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(QueueManagement.class);

    @Autowired
    @Transient
    private QueueManagementRepository queueRepo;

    @Autowired
    @Transient
    private TaskProcessingService taskProcessingService;

    @Autowired
    @Transient
    private CommonData commonData;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "queueId", nullable = false, updatable = false)
    private long queueId;

    @Column(name = "requestTime", nullable = true)
    private Timestamp requestTime;

    @Column(name = "requestType", nullable = true)
    private String requestType;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "oldstatus", nullable = true)
    private String oldStatus;

    @Column(name = "responseId", nullable = true)
    private Long responseId;

    @Column(name = "reason", nullable = true)
    private String reason;

    @Column(name = "startTime", nullable = true)
    private long startTime;

    @Column(name = "queueTime", nullable = true)
    private long queueTime;

    @Column(name = "endTime", nullable = true)
    private long endTime;

    @Column(name = "procesingTime", nullable = true)
    private long procesingTime;

    @Column(name = "documentId", nullable = true)
    private String documentId;

    @Column(name = "documentType", nullable = true)
    private String documentType;

    @Column(name = "documentPath", nullable = true)
    private String documentPath;

    @Column(name = "documentUrl", nullable = true)
    private String documentUrl;

    @Column(name = "rankView", nullable = true)
    private int rank;

    @Column(name = "viewUrl", nullable = true)
    private String viewUrl;

    @Column(name = "language", nullable = true)
    private String language;

    @Column(name = "languageId", nullable = true)
    private int languageId;

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name = "categoryId", nullable = true)
    private int categoryId;

    @Column(name = "topic", nullable = true)
    private String topic;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "topicId", nullable = true)
    private int topicId;

    @Column(name = "videoPath", nullable = true)
    private String videoPath;

    @Column(name = "outlinePath", nullable = true)
    private String outlinePath;

    public long getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(long queueTime) {
        this.queueTime = queueTime;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getOutlinePath() {
        return outlinePath;
    }

    public void setOutlinePath(String outlinePath) {
        this.outlinePath = outlinePath;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getQueueId() {
        return queueId;
    }

    public void setQueueId(long queueId) {
        this.queueId = queueId;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusLog() {
        return "from " + oldStatus + " to " + status;
    }

    public void setStatus(String status) {
        this.oldStatus = this.status;
        this.status = status;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getProcesingTime() {
        return procesingTime;
    }

    public void setProcesingTime(long procesingTime) {
        this.procesingTime = procesingTime;
    }

    public QueueManagement() {
        super();

    }

    public QueueManagement(long queueId, Timestamp request, String requestType, String status, long startTime,
            long endTime, long procesingTime) {
        super();
        this.queueId = queueId;
        this.requestTime = request;
        this.requestType = requestType;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.procesingTime = procesingTime;

    }

    public QueueManagement(long queueId, Timestamp request, String requestType, String status, long startTime,
            long endTime, long procesingTime, String videoPath) {
        super();
        this.queueId = queueId;
        this.requestTime = request;
        this.requestType = requestType;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.procesingTime = procesingTime;
        this.videoPath = videoPath;

    }

    public QueueManagement(long queueId, Timestamp requestTime, String requestType) {
        super();
        this.queueId = queueId;
        this.requestTime = requestTime;
        this.requestType = requestType;
    }

    public QueueManagement(long queueId, String documentId, String documentType, int rank, String language,
            int languageId, String status) {
        super();
        this.queueId = queueId;
        this.documentId = documentId;
        this.documentType = documentType;
        this.rank = rank;
        this.language = language;
        this.languageId = languageId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "QueueManagement [queueId=" + queueId + ", requestTime=" + requestTime + ", requestType=" + requestType
                + ", status=" + status + ", documentId=" + documentId + ", documentType=" + documentType + ", rank="
                + rank + ", language=" + language + "]";
    }

    @Override
    public void run() {
        MDC.put("queueId", '@' + Long.toString(getQueueId()));

        if (taskProcessingService.isURLWorking(commonData.elasticSearch_url)) {

            StringBuilder documentSb = new StringBuilder();
            documentSb.append(commonData.elasticSearch_url);
            documentSb.append("/");
            documentSb.append(getRequestType());
            documentSb.append("/");
            documentSb.append(documentId);
            documentSb.append("/");
            documentSb.append(documentType);
            documentSb.append("/");
            documentSb.append(languageId);

            String api_url = "";

            setStartTime(System.currentTimeMillis());
            setStatus(CommonData.STATUS_PROCESSING);
            logger.info("{}", getStatusLog());

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpUriRequest request = null;

                if (getRequestType().equals(CommonData.ADD_DOCUMENT)
                        || getRequestType().equals(CommonData.UPDATE_DOCUMENT)) {

                    documentSb.append("/");
                    documentSb.append(language);
                    documentSb.append("/");
                    documentSb.append(rank);
                    api_url = documentSb.toString();
                    logger.info("API_URL:{}", api_url);

                    request = new HttpPost(api_url);

                }

                else if (getRequestType().equals(CommonData.UPDATE_DOCUMENT_RANK)
                        || getRequestType().equals(CommonData.DELETE_DOCUMENT)) {

                    if (getRequestType().equals(CommonData.UPDATE_DOCUMENT_RANK)) {

                        documentSb.append("/");

                        documentSb.append(rank);

                    }

                    api_url = documentSb.toString();
                    logger.info("API_URL:{}", api_url);

                   
             
                    request = new HttpGet(api_url);

                }

                if (request instanceof HttpPost) {
                    List<NameValuePair> paramsforAddDocument = new ArrayList<>();
                    paramsforAddDocument.add(new BasicNameValuePair("documentPath", getDocumentPath()));
                    paramsforAddDocument.add(new BasicNameValuePair("documentUrl", getDocumentUrl()));
                    paramsforAddDocument.add(new BasicNameValuePair("view_url", getViewUrl()));
                    paramsforAddDocument.add(new BasicNameValuePair("categoryId", Integer.toString(getCategoryId())));
                    paramsforAddDocument.add(new BasicNameValuePair("category", getCategory()));
                    paramsforAddDocument.add(new BasicNameValuePair("topicId", Integer.toString(getTopicId())));
                    paramsforAddDocument.add(new BasicNameValuePair("topic", getTopic()));
                    if (getOutlinePath() != null && !getOutlinePath().isEmpty()) {
                        paramsforAddDocument.add(new BasicNameValuePair("outlinePath", getOutlinePath()));

                    }

                    
                        paramsforAddDocument.add(new BasicNameValuePair("videoPath", getVideoPath()));

                    
                    
                        paramsforAddDocument.add(new BasicNameValuePair("title", getTitle()));

                    
                   
                        paramsforAddDocument.add(new BasicNameValuePair("description", getDescription()));

                    
                    HttpPost httpPost = (HttpPost) request;
                    httpPost.setEntity(new UrlEncodedFormEntity(paramsforAddDocument, "UTF-8"));

                }

                HttpResponse response = httpClient.execute(request);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200 || statusCode == 201) {
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                    JsonNode publishedArray = jsonNode.get("queueId");
                    if (publishedArray != null) {
                        MDC.put("queueId",
                                '@' + Long.toString(getQueueId()) + '#' + Long.toString(publishedArray.asLong()));
                        setResponseId(publishedArray.asLong());
                        setStatus(CommonData.STATUS_DONE);
                        logger.info("{}", getStatusLog());

                    } else {
                        logger.info("Json Node:{} ", jsonNode);
                        setStatus(CommonData.STATUS_FAILED);
                        logger.info("{}", getStatusLog());
                    }

                } else {
                    logger.info("Status Code:{} API URl:{}", statusCode, api_url);
                    setStatus(CommonData.STATUS_PENDING);
                    logger.info("{}", getStatusLog());

                }

            } catch (Exception e) {
                logger.error("Error", e);

            }

            finally {

                setEndTime(System.currentTimeMillis());
                setProcesingTime(endTime - startTime);
                logger.info("{}", getStatusLog());

                queueRepo.save(this);
                taskProcessingService.getRunningDocuments().remove(documentId);
                MDC.remove("queueId");
            }

        }

    }

}
