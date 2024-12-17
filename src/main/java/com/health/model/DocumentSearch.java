package com.health.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.health.utility.ServiceUtility;

@Component
public class DocumentSearch {

    private String id;

    private String documentContent;

    private String outlineContent;

    private String documentType;

    private String documentId;

    private String language;

    private int languageId;

    private String category;

    private int categoryId;

    private int orderValue;

    private String topic;

    private String videoPath;

    private int topicId;

    private int rank;

    private String viewUrl;

    private String documentUrl;

    private String title;

    private String description;

    private String thumbnailPath;

    private Long creationTime;

    private Long modificationTime;

    private Long changeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
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

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getLanguage() {
        return language;
    }

    public int getTutorialId() {

        try {
            String tutorialIdString = getDocumentId().replaceAll("[^0-9]", "");
            return Integer.parseInt(tutorialIdString);
        } catch (NumberFormatException e) {

            return -1;
        }
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

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOutlineContent() {
        return outlineContent;
    }

    public void setOutlineContent(String outlineContent) {
        this.outlineContent = outlineContent;
    }

    public String getOutlineContentUpto600() {
        if (outlineContent.length() > 557) {
            String tempcontent = outlineContent.substring(0, 557) + "...";
            Document document = Jsoup.parseBodyFragment(tempcontent, "");
            ServiceUtility.addMissingHtmlTag(document.body());
            return document.body().html();
        }
        return outlineContent;
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

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Long modificationTime) {
        this.modificationTime = modificationTime;
    }

    public Long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Long changeTime) {
        this.changeTime = changeTime;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;

    }

    public DocumentSearch() {
        super();
    }

    public DocumentSearch(String id, String documentContent, String documentType, String documentId) {
        super();
        this.id = id;
        this.documentContent = documentContent;
        this.documentType = documentType;
        this.documentId = documentId;
    }

    public DocumentSearch(String id, String documentContent, String documentType, String documentId, String videoPath,
            int orderValue) {
        super();
        this.id = id;
        this.documentContent = documentContent;
        this.documentType = documentType;
        this.documentId = documentId;
        this.videoPath = videoPath;
        this.orderValue = orderValue;
    }

    public DocumentSearch(String id, String documentContent) {
        super();
        this.id = id;
        this.documentContent = documentContent;
    }

    @Override
    public String toString() {
        return "DocumentSearch [documentType=" + documentType + ", documentId=" + documentId + ", language=" + language
                + ", rank=" + rank + ", viewUrl=" + viewUrl + ", videoPath=" + videoPath + ", creationTime="
                + creationTime + ", modificationTime=" + modificationTime + ", changeTime=" + changeTime
                + ", orderValue=" + orderValue + "]";
    }

}
