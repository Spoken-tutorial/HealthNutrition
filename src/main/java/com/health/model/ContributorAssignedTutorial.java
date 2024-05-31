package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Pre-tutorial Object to store Pre-tutorial related data on database
 */
@Entity
@Table(name = "contributor_Role")
public class ContributorAssignedTutorial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private Timestamp dateAdded;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topicCat_ID")
    private TopicCategoryMapping topicCatId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id")
    private Language lan;

    @OneToMany(mappedBy = "conAssignedTutorial", cascade = CascadeType.ALL)
    private Set<Tutorial> tutorials = new HashSet<Tutorial>();

    @OneToMany(mappedBy = "conAssignedTutorial", cascade = CascadeType.ALL)
    private Set<ContributorAssignedMultiUserTutorial> multiUserAssigned = new HashSet<ContributorAssignedMultiUserTutorial>();

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

    public TopicCategoryMapping getTopicCatId() {
        return topicCatId;
    }

    public void setTopicCatId(TopicCategoryMapping topicCatId) {
        this.topicCatId = topicCatId;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public ContributorAssignedTutorial(int id, Timestamp dateAdded, TopicCategoryMapping topicCatId, Language lan) {
        super();
        this.id = id;
        this.dateAdded = dateAdded;
        this.topicCatId = topicCatId;
        this.lan = lan;
    }

    public ContributorAssignedTutorial() {

    }

    public Set<Tutorial> getTutorials() {
        return tutorials;
    }

    public void setTutorials(Set<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }

    public Set<ContributorAssignedMultiUserTutorial> getMultiUserAssigned() {
        return multiUserAssigned;
    }

    public void setMultiUserAssigned(Set<ContributorAssignedMultiUserTutorial> multiUserAssigned) {
        this.multiUserAssigned = multiUserAssigned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ContributorAssignedTutorial [id=").append(id);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append("]");
        return sb.toString();
    }

}
