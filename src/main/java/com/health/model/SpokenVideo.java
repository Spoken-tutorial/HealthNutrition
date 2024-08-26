package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * category Object to store category related data on database
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Entity

public class SpokenVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(name = "display_name", nullable = false, unique = true)
    private String displayName;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    /**
     * Timestamp on added
     */
    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    /**
     * relative path of image stored
     */
    @Column(name = "file_path", nullable = false)
    private String filePath;

    /**
     * description of category
     */
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    /**
     * user who created it
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    @Override
    public String toString() {
        return "SpokenVideo [id=" + id + ", displayName=" + displayName + ", fileName=" + fileName + ", dateAdded="
                + dateAdded + ", filePath=" + filePath + ", fileSize=" + fileSize + ", user=" + user + "]";
    }

}
