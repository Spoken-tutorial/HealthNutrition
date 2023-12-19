package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PathofPromoVideo {

    @Id
    int pathPromoId;

    @Column(name = "date_added")
    private Timestamp dateAdded;

    @Column(name = "video_path")
    private String videoPath;

    @ManyToOne
    @JoinColumn(name = "promo_id")
    private PromoVideo promoVideo;

    @ManyToOne
    @JoinColumn(name = "lan_id")
    private Language lan;

    public PathofPromoVideo() {

    }

    public PathofPromoVideo(int pathPromoId, Timestamp dateAdded, String videoPath, PromoVideo promoVideo,
            Language lan) {
        super();
        this.pathPromoId = pathPromoId;
        this.dateAdded = dateAdded;
        this.videoPath = videoPath;
        this.promoVideo = promoVideo;
        this.lan = lan;
    }

    public PathofPromoVideo(Timestamp dateAdded, String videoPath, PromoVideo promoVideo, Language lan) {
        super();
        this.dateAdded = dateAdded;
        this.videoPath = videoPath;
        this.promoVideo = promoVideo;
        this.lan = lan;
    }

    public int getBroFileId() {
        return pathPromoId;
    }

    public void setBroFileId(int pathPromoId) {
        this.pathPromoId = pathPromoId;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public PromoVideo getPromoVideo() {
        return promoVideo;
    }

    public void setPromoVideo(PromoVideo promoVideo) {
        this.promoVideo = promoVideo;
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
        sb.append("PathofPromoVideo [pathPromoId=").append(pathPromoId);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", videoPath=").append(videoPath);

        sb.append("]");
        return sb.toString();
    }

}
