package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tutorial_with_week_pack")
public class TutorialWithWeekAndPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "week_id", nullable = false)
    private Week week;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private PackageContainer packageContainer;

    @ManyToOne
    @JoinColumn(name = "video_resource_id", nullable = false)
    private VideoResource videoResource;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public PackageContainer getPackageContainer() {
        return packageContainer;
    }

    public void setPackageContainer(PackageContainer packageContainer) {
        this.packageContainer = packageContainer;
    }

    public VideoResource getVideoResource() {
        return videoResource;
    }

    public void setVideoResource(VideoResource videoResource) {
        this.videoResource = videoResource;
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

    public TutorialWithWeekAndPackage(String title, Timestamp dateAdded, boolean status, Week week,
            PackageContainer packageContainer, VideoResource videoResource) {

        this.title = title;
        this.dateAdded = dateAdded;
        this.status = status;
        this.week = week;
        this.packageContainer = packageContainer;
        this.videoResource = videoResource;
    }

    public TutorialWithWeekAndPackage(String title, Timestamp dateAdded, Week week, PackageContainer packageContainer,
            VideoResource videoResource) {

        this.title = title;
        this.dateAdded = dateAdded;
        this.week = week;
        this.packageContainer = packageContainer;
        this.videoResource = videoResource;
    }

    public TutorialWithWeekAndPackage() {

    }

//    public static Comparator<TutorialWithWeekAndPackage> SortByUploadTime = new Comparator<TutorialWithWeekAndPackage>() {
//
//        @Override
//        public int compare(TutorialWithWeekAndPackage t1, TutorialWithWeekAndPackage t2) {
//            return t2.getDateAdded().compareTo(t1.getDateAdded());
//
//        }
//    };

}
