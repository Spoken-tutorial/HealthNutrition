package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LiveTutorial implements Comparable<LiveTutorial>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "live_tutorial_id", nullable = false, updatable = false)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @ManyToOne
    @JoinColumn(name = "lan_id", nullable = false)
    private Language lan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public LiveTutorial() {
        super();

    }

    public LiveTutorial(int id, String name, String title, String url, Timestamp dateAdded, Language lan) {

        this.id = id;
        this.name = name;
        this.title = title;
        this.url = url;
        this.dateAdded = dateAdded;

        this.lan = lan;
    }

    public LiveTutorial(String name, String title, String url, Timestamp dateAdded, Language lan) {

        this.name = name;
        this.title = title;
        this.url = url;
        this.dateAdded = dateAdded;

        this.lan = lan;
    }

    public static Comparator<LiveTutorial> SortByUploadTime = new Comparator<LiveTutorial>() {

        // Method
        @Override
        public int compare(LiveTutorial t1, LiveTutorial t2) {
            return t2.getDateAdded().compareTo(t1.getDateAdded());

        }
    };

    @Override
    public int compareTo(LiveTutorial o) {

        return this.getName().compareTo(o.getName());
    }

}
