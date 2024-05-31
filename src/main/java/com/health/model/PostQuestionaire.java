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
public class PostQuestionaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String questionPath;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Training_id")
    private TrainingInformation traineeInfos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TrainingInformation getTraineeInfos() {
        return traineeInfos;
    }

    public void setTraineeInfos(TrainingInformation traineeInfos) {
        this.traineeInfos = traineeInfos;
    }

    public PostQuestionaire(int id, String questionPath, Timestamp dateAdded, TrainingInformation traineeInfos,
            User user) {
        super();
        this.id = id;
        this.questionPath = questionPath;
        this.dateAdded = dateAdded;
        this.traineeInfos = traineeInfos;
        this.user = user;
    }

    public PostQuestionaire() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PostQuestionaire [id=").append(id);
        sb.append(", questionPath=").append(questionPath);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append("]");
        return sb.toString();
    }

}