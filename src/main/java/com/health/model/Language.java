
package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.health.domain.security.UserRole;

@Entity
public class Language implements Comparable<Language>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lan_id", updatable = false, nullable = false)
    private int lanId;

    @Column(nullable = false)

    private String langName;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles = new HashSet<UserRole>();

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<Question>();

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ContributorAssignedTutorial> conAssignedTutorial = new HashSet<ContributorAssignedTutorial>();

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TrainingInformation> trainingInfos = new HashSet<TrainingInformation>();

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Brouchure> brouchure = new HashSet<Brouchure>();

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FilesofBrouchure> filesofBrouchure = new HashSet<FilesofBrouchure>();

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PathofPromoVideo> pathofPromoVideo = new HashSet<PathofPromoVideo>();

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LiveTutorial> liveTutorial = new HashSet<LiveTutorial>();

    public Set<LiveTutorial> getLiveTutorial() {
        return liveTutorial;
    }

    public void setLiveTutorial(Set<LiveTutorial> liveTutorial) {
        this.liveTutorial = liveTutorial;
    }

    public Set<Brouchure> getBrouchure() {
        return brouchure;
    }

    public void setBrouchure(Set<Brouchure> brouchure) {
        this.brouchure = brouchure;
    }

    public Set<PathofPromoVideo> getPathofPromoVideo() {
        return pathofPromoVideo;
    }

    public void setPathofPromoVideo(Set<PathofPromoVideo> pathofPromoVideo) {
        this.pathofPromoVideo = pathofPromoVideo;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<FilesofBrouchure> getFilesofBrouchure() {
        return filesofBrouchure;
    }

    public void setFilesofBrouchure(Set<FilesofBrouchure> filesofBrouchure) {
        this.filesofBrouchure = filesofBrouchure;
    }

    @OneToMany(mappedBy = "lan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<Event>();

    public int getLanId() {
        return lanId;
    }

    public void setLanId(int lanId) {
        this.lanId = lanId;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<ContributorAssignedTutorial> getConAssignedTutorial() {
        return conAssignedTutorial;
    }

    public void setConAssignedTutorial(Set<ContributorAssignedTutorial> conAssignedTutorial) {
        this.conAssignedTutorial = conAssignedTutorial;
    }

    public Set<TrainingInformation> getTrainingInfos() {
        return trainingInfos;
    }

    public void setTrainingInfos(Set<TrainingInformation> trainingInfos) {
        this.trainingInfos = trainingInfos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Language [lanId=").append(lanId);
        sb.append(", langName=").append(langName);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int compareTo(Language o) {

        return this.getLangName().compareTo(o.getLangName());
    }

}
