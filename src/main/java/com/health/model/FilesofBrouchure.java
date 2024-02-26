package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FilesofBrouchure {

    @Id
    int broFileId;

    @Column(name = "date_added")
    private Timestamp dateAdded;

    @Column(name = "Web_path")
    private String webPath;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "added_queue", nullable = false)
    private boolean addedQueue = false;

    @ManyToOne
    @JoinColumn(name = "ver_id")
    private Version version;

    @ManyToOne
    @JoinColumn(name = "lan_id")
    private Language lan;

    public FilesofBrouchure() {
        super();

    }

    public FilesofBrouchure(int broFileId, Timestamp dateAdded, String webPath, String thumbnailPath, Version version,
            Language lan) {
        super();
        this.broFileId = broFileId;
        this.dateAdded = dateAdded;
        this.webPath = webPath;
        this.thumbnailPath = thumbnailPath;
        this.version = version;
        this.lan = lan;
    }

    public FilesofBrouchure(int broFileId, Timestamp dateAdded, String webPath) {
        super();
        this.broFileId = broFileId;
        this.dateAdded = dateAdded;
        this.webPath = webPath;

    }

    public FilesofBrouchure(int broFileId, Timestamp dateAdded, String webPath, Language lan) {
        super();
        this.broFileId = broFileId;
        this.dateAdded = dateAdded;
        this.webPath = webPath;
        this.lan = lan;
    }

    public FilesofBrouchure(int broFileId, Timestamp dateAdded, String webPath, Version version) {
        super();
        this.broFileId = broFileId;
        this.dateAdded = dateAdded;
        this.webPath = webPath;
        this.version = version;

    }

    public boolean isAddedQueue() {
        return addedQueue;
    }

    public void setAddedQueue(boolean addedQueue) {
        this.addedQueue = addedQueue;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public int getBroFileId() {
        return broFileId;
    }

    public void setBroFileId(int broFileId) {
        this.broFileId = broFileId;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FilesofBrouchure [broFileId=").append(broFileId);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", webPath=").append(webPath);
        sb.append(", thumbnailPath=").append(thumbnailPath);
        sb.append("]");
        return sb.toString();
    }

}
