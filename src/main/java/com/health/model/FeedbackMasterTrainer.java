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
public class FeedbackMasterTrainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int TrainerFeedId;

    @Column(length = 2000)
    private String description;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Training_id")
    private TrainingInformation traineeInfos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return TrainerFeedId;
    }

    public void setId(int id) {
        this.TrainerFeedId = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public TrainingInformation getTraineeInfos() {
        return traineeInfos;
    }

    public void setTraineeInfos(TrainingInformation traineeInfos) {
        this.traineeInfos = traineeInfos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FeedbackMasterTrainer(int trainerFeedId, String description, Timestamp dateAdded, String path,
            TrainingInformation traineeInfos, User user) {
        super();
        TrainerFeedId = trainerFeedId;
        this.description = description;
        this.dateAdded = dateAdded;
        this.path = path;
        this.traineeInfos = traineeInfos;
        this.user = user;
    }

    public FeedbackMasterTrainer() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FeedbackMasterTrainer [TrainerFeedId=").append(TrainerFeedId);
        sb.append(", description=").append(description);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", path=").append(path);
        sb.append("]");
        return sb.toString();
    }

}