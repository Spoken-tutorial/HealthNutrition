package com.health.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "lan_id", nullable = false)
    private Language lan;

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

    public VideoResource(int id, String fileName, String videoPath, Language lan) {
        super();
        this.id = id;
        this.fileName = fileName;
        this.videoPath = videoPath;
        this.lan = lan;
    }

}
