package com.health.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity

public class HistoricalData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    private int categoryId;

    @Column(nullable = false)
    private int topicId;

    @Column(nullable = false)
    private int languageId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @Column(name = "language_name", nullable = false)
    private String languageName;

    @ManyToOne
    @JoinColumn(name = "tutorial_id", nullable = false)
    private Tutorial tutorial;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
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

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public HistoricalData(int categoryId, int topicId, int languageId, String categoryName, String topicName,
            String languageName, Tutorial tutorial) {
        super();
        this.categoryId = categoryId;
        this.topicId = topicId;
        this.languageId = languageId;
        this.categoryName = categoryName;
        this.topicName = topicName;
        this.languageName = languageName;
        this.tutorial = tutorial;
    }

    public HistoricalData() {

    }

}
