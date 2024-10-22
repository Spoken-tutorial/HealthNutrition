
package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class WeekTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int weekTitleId;

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
    @JoinColumn(name = "video_resource_id", nullable = false)
    private VideoResource videoResource;

    @OneToMany(mappedBy = "weekTitle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TutorialWithWeekAndPackage> tutorialsWithWeekAndPack = new HashSet<TutorialWithWeekAndPackage>();

    public int getWeekTitleId() {
        return weekTitleId;
    }

    public void setWeekTitleId(int weekTitleId) {
        this.weekTitleId = weekTitleId;
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

    public WeekTitle() {
        super();

    }

    public Set<TutorialWithWeekAndPackage> getTutorialsWithWeekAndPack() {
        return tutorialsWithWeekAndPack;
    }

    public void setTutorialsWithWeekAndPack(Set<TutorialWithWeekAndPackage> tutorialsWithWeekAndPack) {
        this.tutorialsWithWeekAndPack = tutorialsWithWeekAndPack;
    }

    public WeekTitle(int weekTitleId, String title, Timestamp dateAdded, Week week, VideoResource videoResource) {
        super();
        this.weekTitleId = weekTitleId;
        this.title = title;
        this.dateAdded = dateAdded;
        this.week = week;
        this.videoResource = videoResource;
    }

    public WeekTitle(String title, Timestamp dateAdded, Week week, VideoResource videoResource) {
        super();
        this.title = title;
        this.dateAdded = dateAdded;
        this.week = week;
        this.videoResource = videoResource;
    }

    @Override
    public String toString() {
        return "WeekTitle [weekTitleId=" + weekTitleId + ", title=" + title + ", week=" + week + ", videoResource="
                + videoResource + "]";
    }

}
