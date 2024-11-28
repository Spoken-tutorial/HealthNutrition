package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
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
import javax.persistence.Table;

@Entity
@Table(name = "video_resource")
public class VideoResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "video_path", nullable = false, unique = true)
    private String videoPath;

    @Column(name = "thumbnail_path", nullable = true)
    private String thumbnailPath;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @ManyToOne
    @JoinColumn(name = "lan_id", nullable = false)
    private Language lan;

    @OneToMany(mappedBy = "videoResource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WeekTitleVideo> weekTitleVideos = new HashSet<WeekTitleVideo>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getFileName() {
        return fileName;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public VideoResource() {
        super();

    }

    public Set<WeekTitleVideo> getWeekTitles() {
        return weekTitleVideos;
    }

    public void setWeekTitles(Set<WeekTitleVideo> weekTitleVideos) {
        this.weekTitleVideos = weekTitleVideos;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public VideoResource(String fileName, String videoPath, String thumbnailPath, Timestamp dateAdded, Language lan) {
        super();
        this.fileName = fileName;
        this.videoPath = videoPath;
        this.thumbnailPath = thumbnailPath;
        this.dateAdded = dateAdded;
        this.lan = lan;
    }

    public static Comparator<VideoResource> SortByUploadTime = new Comparator<VideoResource>() {

        @Override
        public int compare(VideoResource t1, VideoResource t2) {
            return t2.getDateAdded().compareTo(t1.getDateAdded());

        }
    };
}
