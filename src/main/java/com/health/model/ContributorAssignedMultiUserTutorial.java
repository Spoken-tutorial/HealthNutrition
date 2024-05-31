package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * userAssignedTutorial Object to store userAssignedTutorial related data on
 * database
 */
@Entity
@Table(name = "UserOnTutorialAssigned")
public class ContributorAssignedMultiUserTutorial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_assigned")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conAssignedTutorial")
    private ContributorAssignedTutorial conAssignedTutorial;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ContributorAssignedTutorial getConAssignedTutorial() {
        return conAssignedTutorial;
    }

    public void setConAssignedTutorial(ContributorAssignedTutorial conAssignedTutorial) {
        this.conAssignedTutorial = conAssignedTutorial;
    }

    public ContributorAssignedMultiUserTutorial() {

    }

    public ContributorAssignedMultiUserTutorial(int id, Timestamp dateAdded, User user,
            ContributorAssignedTutorial conAssignedTutorial) {
        super();
        this.id = id;
        this.dateAdded = dateAdded;
        this.user = user;
        this.conAssignedTutorial = conAssignedTutorial;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ContributorAssignedMultiUserTutorial [id=").append(id);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append("]");
        return sb.toString();
    }

}
