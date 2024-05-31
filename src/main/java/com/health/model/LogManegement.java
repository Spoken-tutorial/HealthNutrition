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
public class LogManegement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int logId;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    /**
     * Component of tutorial on which activity is made
     */
    private String type;

    /**
     * new state
     */
    private int statusChangedTo;

    /**
     * previous state
     */
    private int statusPrevious;

    private String userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Tutorial_id")
    private Tutorial tutorialInfos;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatusChangedTo() {
        return statusChangedTo;
    }

    public void setStatusChangedTo(int statusChangedTo) {
        this.statusChangedTo = statusChangedTo;
    }

    public int getStatusPrevious() {
        return statusPrevious;
    }

    public void setStatusPrevious(int statusPrevious) {
        this.statusPrevious = statusPrevious;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tutorial getTutorialInfos() {
        return tutorialInfos;
    }

    public void setTutorialInfos(Tutorial tutorialInfos) {
        this.tutorialInfos = tutorialInfos;
    }

    public LogManegement(int logId, Timestamp dateAdded, String type, int statusChangedTo, int statusPrevious,
            String userRole, User user, Tutorial tutorialInfos) {
        super();
        this.logId = logId;
        this.dateAdded = dateAdded;
        this.type = type;
        this.statusChangedTo = statusChangedTo;
        this.statusPrevious = statusPrevious;
        this.userRole = userRole;
        this.user = user;
        this.tutorialInfos = tutorialInfos;
    }

    public LogManegement() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LogManegement [logId=").append(logId);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", type=").append(type);
        sb.append(", statusChangedTo=").append(statusChangedTo);
        sb.append(", statusPrevious=").append(statusPrevious);
        sb.append("]");
        return sb.toString();
    }

}
