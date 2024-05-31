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
public class Consultant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "consult_id", updatable = false, nullable = false)
    private int consultantId;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "showonhomepage", nullable = false)
    private boolean onHome = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public int getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(int consultantId) {
        this.consultantId = consultantId;
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

    public boolean isOnHome() {
        return onHome;
    }

    public void setOnHome(boolean onHome) {
        this.onHome = onHome;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Consultant [consultantId=").append(consultantId);
        sb.append(", description=").append(description);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", onHome=").append(onHome);
        sb.append("]");
        return sb.toString();
    }

}
