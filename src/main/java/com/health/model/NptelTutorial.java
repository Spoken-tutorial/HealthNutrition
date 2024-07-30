
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
public class NptelTutorial implements Comparable<NptelTutorial>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nptel_tutorial_id", nullable = false, updatable = false)
    private int nptelTutorialId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "video_url", nullable = false, unique = true)
    private String videoUrl;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "pack_lan_week_id", nullable = false)
    private PackLanWeekMapping packLanWeek;

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

    public PackLanWeekMapping getPackLanWeek() {
        return packLanWeek;
    }

    public void setPackLanWeek(PackLanWeekMapping packLanWeek) {
        this.packLanWeek = packLanWeek;
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

        // Method
        @Override
        public int compare(NptelTutorial t1, NptelTutorial t2) {
            return t2.getDateAdded().compareTo(t1.getDateAdded());

        }
    };

    @Override
    public int compareTo(NptelTutorial o) {

        return this.getTitle().compareTo(o.getTitle());
    }

}
