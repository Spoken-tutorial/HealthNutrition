package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "topic_lan_mapping")
public class TopicLanMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_lan_id", nullable = false, updatable = false)
    private int topicLanId;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "lang_id")
    private Language lan;

    @OneToMany(mappedBy = "topicLanMapping", cascade = CascadeType.ALL)
    private Set<TrainingResource> trainingResources = new HashSet<TrainingResource>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getTopicLanId() {
        return topicLanId;
    }

    public void setTopicLanId(int topicLanId) {
        this.topicLanId = topicLanId;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public Set<TrainingResource> getTrainingResources() {
        return trainingResources;
    }

    public void setTrainingResources(Set<TrainingResource> trainingResources) {
        this.trainingResources = trainingResources;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public TopicLanMapping() {

    }

    public TopicLanMapping(Timestamp dateAdded, Topic topic, Language lan) {

        this.dateAdded = dateAdded;
        this.topic = topic;
        this.lan = lan;
    }

    public static Comparator<TopicLanMapping> SortByTopicName = new Comparator<TopicLanMapping>() {

        @Override
        public int compare(TopicLanMapping t1, TopicLanMapping t2) {

            return t1.getTopic().getTopicName().compareTo(t2.getTopic().getTopicName());

        }
    };

}
