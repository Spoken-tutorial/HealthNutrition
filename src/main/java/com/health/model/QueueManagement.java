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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.repository.QueueManagementRepository;
import com.health.utility.CommonData;

@Entity
public class QueueManagement implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(QueueManagement.class);

    @Autowired
    @Transient
    private QueueManagementRepository queueRepo;

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

    @Column(name = "topicId", nullable = true)
    private int topicId;

    @Column(name = "outlinePath", nullable = true)
    private String outlinePath;

    public long getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(long queueTime) {
        this.queueTime = queueTime;
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

    public void setStatus(String status) {
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

        StringBuilder addDocumentsb = new StringBuilder();
        addDocumentsb.append(commonData.elasticSearch_url);
        addDocumentsb.append("/");
        addDocumentsb.append("addDocument");
        addDocumentsb.append("/");
        addDocumentsb.append(documentId);
        addDocumentsb.append("/");
        addDocumentsb.append(documentType);
        addDocumentsb.append("/");
        addDocumentsb.append(languageId);
        addDocumentsb.append("/");
        addDocumentsb.append(language);
        addDocumentsb.append("/");
        addDocumentsb.append(rank);
        String api_url_addDocument = addDocumentsb.toString();

        StringBuilder updateDocumentsb = new StringBuilder();
        updateDocumentsb.append(commonData.elasticSearch_url);
        updateDocumentsb.append("/");
        updateDocumentsb.append("updateDocument");
        updateDocumentsb.append("/");
        updateDocumentsb.append(documentId);
        updateDocumentsb.append("/");
        updateDocumentsb.append(documentType);
        updateDocumentsb.append("/");
        updateDocumentsb.append(languageId);
        updateDocumentsb.append("/");
        updateDocumentsb.append(language);
        updateDocumentsb.append("/");
        updateDocumentsb.append(rank);
        String api_url_updateDocument = updateDocumentsb.toString();

        StringBuilder updateDocumentRanksb = new StringBuilder();
        updateDocumentRanksb.append(commonData.elasticSearch_url);
        updateDocumentRanksb.append("/");
        updateDocumentRanksb.append("updateDocumentRank");
        updateDocumentRanksb.append("/");
        updateDocumentRanksb.append(documentId);
        updateDocumentRanksb.append("/");
        updateDocumentRanksb.append(documentType);
        updateDocumentRanksb.append("/");
        updateDocumentRanksb.append(languageId);
        updateDocumentRanksb.append("/");

        updateDocumentRanksb.append(rank);
        String api_url_updateDocumentRank = updateDocumentRanksb.toString();

        StringBuilder deleteDocumentsb = new StringBuilder();
        deleteDocumentsb.append(commonData.elasticSearch_url);
        deleteDocumentsb.append("/");
        deleteDocumentsb.append("deleteDocument");
        deleteDocumentsb.append("/");
        deleteDocumentsb.append(documentId);
        deleteDocumentsb.append("/");
        deleteDocumentsb.append(documentType);
        deleteDocumentsb.append("/");
        deleteDocumentsb.append(languageId);

        String api_url_deleteDocument = deleteDocumentsb.toString();

        System.out.println(api_url_addDocument);
        System.out.println(api_url_updateDocument);
        System.out.println(api_url_updateDocumentRank);
        System.out.println(api_url_deleteDocument);

        try {

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpPost httpPost_addDocument = new HttpPost(api_url_addDocument);

            List<NameValuePair> paramsforAddDocument = new ArrayList<>();
            paramsforAddDocument.add(new BasicNameValuePair("documentPath", "Media\\TestHtml.html"));
            paramsforAddDocument.add(new BasicNameValuePair("documentUrl", "TestDocumentUrl"));
            paramsforAddDocument.add(new BasicNameValuePair("view_url", "TestViewUrl"));
            httpPost_addDocument.setEntity(new UrlEncodedFormEntity(paramsforAddDocument, "UTF-8"));

            HttpResponse response_addDocument = httpClient.execute(httpPost_addDocument);

            int statusCode__addDocument = response_addDocument.getStatusLine().getStatusCode();

            if (statusCode__addDocument == 200 || statusCode__addDocument == 201) {
                String jsonResponse = EntityUtils.toString(response_addDocument.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                System.out.println("jsonNode" + jsonNode);

                JsonNode publishedArray = jsonNode.get("queueId");
                if (publishedArray != null) {
                    System.out.println("publishedArray: " + publishedArray.asLong());
                    setResponseId(publishedArray.asLong());
                    queueRepo.save(this);
                }

            } else {
                System.out.println(
                        "API request failed with status code: " + statusCode__addDocument + " for AddDocument");

            }

            HttpPost httpPost_updateDocument = new HttpPost(api_url_updateDocument);
            List<NameValuePair> paramsforUpdate = new ArrayList<>();
            paramsforUpdate.add(new BasicNameValuePair("documentPath", "Media\\TestHtml.html"));
            paramsforUpdate.add(new BasicNameValuePair("documentUrl", "TestDocumentUrl"));
            paramsforUpdate.add(new BasicNameValuePair("view_url", "TestViewUrl"));
            httpPost_updateDocument.setEntity(new UrlEncodedFormEntity(paramsforUpdate, "UTF-8"));

            HttpResponse response_updateDocument = httpClient.execute(httpPost_updateDocument);

            int statusCode__updateDocument = response_updateDocument.getStatusLine().getStatusCode();

            if (statusCode__updateDocument == 200 || statusCode__updateDocument == 201) {
                String jsonResponse = EntityUtils.toString(response_updateDocument.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                System.out.println("jsonNode" + jsonNode);

                JsonNode publishedArray = jsonNode.get("queueId");
                if (publishedArray != null) {
                    System.out.println("publishedArray" + publishedArray.asLong());
                    setResponseId(publishedArray.asLong());
                    queueRepo.save(this);
                }

            } else {
                System.out.println(
                        "API request failed with status code: " + statusCode__updateDocument + " for updateDocument");

            }

            HttpGet httpGet_updateDocumentRank = new HttpGet(api_url_updateDocumentRank);

            HttpResponse response_updateDocumentRank = httpClient.execute(httpGet_updateDocumentRank);

            int statusCode__updateDocumentRank = response_updateDocumentRank.getStatusLine().getStatusCode();

            if (statusCode__updateDocumentRank == 200 || statusCode__updateDocumentRank == 201) {
                String jsonResponse = EntityUtils.toString(response_updateDocumentRank.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                System.out.println("jsonNode" + jsonNode);

                JsonNode publishedArray = jsonNode.get("queueId");
                if (publishedArray != null) {
                    System.out.println("publishedArray" + publishedArray.asLong());
                    setResponseId(publishedArray.asLong());
                    queueRepo.save(this);
                }

            } else {
                System.out.println("API request failed with status code: " + statusCode__updateDocumentRank
                        + " for updateDocumentRank");

            }

//            HttpGet httpGet_deleteDocument = new HttpGet(api_url_deleteDocument);
//
//            HttpResponse response_deleteDocument = httpClient.execute(httpGet_deleteDocument);
//
//            int statusCode__deleteDocument = response_deleteDocument.getStatusLine().getStatusCode();
//
//            if (statusCode__deleteDocument == 200 || statusCode__deleteDocument == 201) {
//                String jsonResponse = EntityUtils.toString(response_deleteDocument.getEntity());
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
//                System.out.println("jsonNode" + jsonNode);
//
//                JsonNode publishedArray = jsonNode.get("queueId");
//                if (publishedArray != null) {
//                    System.out.println("publishedArray" + publishedArray.asLong());
//                    setResponseId(publishedArray.asLong());
//                    queueRepo.save(this);
//                }
//
//            } else {
//                System.out.println(
//                        "API request failed with status code: " + statusCode__deleteDocument + " for deleteDocument");
//
//            }

            httpClient.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
