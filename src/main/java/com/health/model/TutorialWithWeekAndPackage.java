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

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "week_title_id", nullable = false)
    private WeekTitleVideo weekTitleVideo;

    @ManyToOne
    @JoinColumn(name = "package_lan_id", nullable = false)
    private PackageLanguage packageLanguage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeekTitleVideo getWeekTitle() {
        return weekTitleVideo;
    }

    public void setWeekTitle(WeekTitleVideo weekTitleVideo) {
        this.weekTitleVideo = weekTitleVideo;
    }

    public PackageLanguage getPackageLanguage() {
        return packageLanguage;
    }

    public void setPackageLanguage(PackageLanguage packageLanguage) {
        this.packageLanguage = packageLanguage;
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

    public TutorialWithWeekAndPackage(Timestamp dateAdded, boolean status, WeekTitleVideo weekTitleVideo,
            PackageLanguage packageLanguage) {
        super();
        this.dateAdded = dateAdded;
        this.status = status;
        this.weekTitleVideo = weekTitleVideo;
        this.packageLanguage = packageLanguage;

    }

    public TutorialWithWeekAndPackage(Timestamp dateAdded, WeekTitleVideo weekTitleVideo,
            PackageLanguage packageLanguage) {
        super();
        this.dateAdded = dateAdded;
        this.weekTitleVideo = weekTitleVideo;
        this.packageLanguage = packageLanguage;

    }

    public TutorialWithWeekAndPackage() {
        super();
    }

    @Override
    public String toString() {
        return "TutorialWithWeekAndPackage [weekTitleVideo=" + weekTitleVideo + ", packageLanguage=" + packageLanguage
                + "]";
    }

}
