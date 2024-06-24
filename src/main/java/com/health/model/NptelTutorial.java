
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
public class NptelTutorial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nptel_tutorial_id", nullable = false, updatable = false)
    private int nptelTutorialId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    @Column(name = "week", nullable = false)
    private int week;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private PackageEntity packageEntity;

    @ManyToOne
    @JoinColumn(name = "lan_id", nullable = false)
    private Language lan;

    public String getTitle() {
        return title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public int getNptelTutorialId() {
        return nptelTutorialId;
    }

    public void setNptelTutorialId(int nptelTutorialId) {
        this.nptelTutorialId = nptelTutorialId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public PackageEntity getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public NptelTutorial() {
        super();

    }

    public NptelTutorial(int nptelTutorialId, String title, String videoUrl, Timestamp dateAdded) {
        super();
        this.nptelTutorialId = nptelTutorialId;
        this.title = title;
        this.videoUrl = videoUrl;
        this.dateAdded = dateAdded;
    }

    public static Comparator<NptelTutorial> SortByUploadTime = new Comparator<NptelTutorial>() {

        @Override
        public int compare(NptelTutorial t1, NptelTutorial t2) {
            return t2.getDateAdded().compareTo(t1.getDateAdded());

        }
    };

}
