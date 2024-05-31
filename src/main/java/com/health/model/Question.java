package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "question_id", updatable = false, nullable = false)
    private int questionId;

    @Column(name = "question_path", nullable = false)
    private String questionPath;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lan_id")
    private Language lan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_cat_id")
    private TopicCategoryMapping topicCatId;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionPath() {
        return questionPath;
    }

    public void setQuestionPath(String questionPath) {
        this.questionPath = questionPath;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public TopicCategoryMapping getTopicCatId() {
        return topicCatId;
    }

    public void setTopicCatId(TopicCategoryMapping topicCatId) {
        this.topicCatId = topicCatId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question [questionId=").append(questionId);
        sb.append(", questionPath=").append(questionPath);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append("]");
        return sb.toString();
    }

}
