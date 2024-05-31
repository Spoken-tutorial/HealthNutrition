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
public class Testimonial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "testi_id", updatable = false, nullable = false)
    private int testimonialId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "filePath", nullable = false)
    private String filePath;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "approved", nullable = false)
    private boolean approved = true;

    @Column(name = "consent")
    String consentLetter = null;

    @Column(name = "thumbnailPath")
    private String thumbnailPath;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Training_id")
    private TrainingInformation traineeInfos;

    public int getTestimonialId() {
        return testimonialId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public void setTestimonialId(int testimonialId) {
        this.testimonialId = testimonialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public TrainingInformation getTraineeInfos() {
        return traineeInfos;
    }

    public void setTraineeInfos(TrainingInformation traineeInfos) {
        this.traineeInfos = traineeInfos;
    }

    public String getConsentLetter() {
        return consentLetter;
    }

    public void setConsentLetter(String consentLetter) {
        this.consentLetter = consentLetter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Testimonial [testimonialId=").append(testimonialId);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", filePath=").append(filePath);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", approved=").append(approved);
        sb.append(", consentLetter=").append(consentLetter);
        sb.append(", thumbnailPath=").append(thumbnailPath);
        sb.append("]");
        return sb.toString();
    }

}
