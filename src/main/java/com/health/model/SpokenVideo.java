package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "spoken_video")
public class SpokenVideo {

    @Id
    @Column(nullable = false, updatable = false)
    private int spokenVideoId;

    @Column(name = "display_name", nullable = false, unique = true)
    private String displayName;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "file_path", nullable = false, unique = true)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    /**
     * user who created it
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id")
    private Language lan;

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getSpokenVideoId() {
        return spokenVideoId;
    }

    public void setSpokenVideoId(int spokenVideoId) {
        this.spokenVideoId = spokenVideoId;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public SpokenVideo() {

    }

    public SpokenVideo(int spokenVideoId, String displayName, String fileName, Timestamp dateAdded, String filePath,
            Long fileSize, User user, Language lan) {
        super();
        this.spokenVideoId = spokenVideoId;
        this.displayName = displayName;
        this.fileName = fileName;
        this.dateAdded = dateAdded;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.user = user;
        this.lan = lan;
    }

    @Override
    public String toString() {
        return "SpokenVideo [spokenVideoId=" + spokenVideoId + ", displayName=" + displayName + ", fileName=" + fileName
                + ", dateAdded=" + dateAdded + ", filePath=" + filePath + ", fileSize=" + fileSize + "]";
    }

}
