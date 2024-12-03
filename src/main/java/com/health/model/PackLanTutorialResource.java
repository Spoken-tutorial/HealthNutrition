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

@Entity

public class PackLanTutorialResource implements Serializable {

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
    @JoinColumn(name = "tutorial_id", nullable = false)
    private Tutorial tutorial;

    @ManyToOne
    @JoinColumn(name = "package_lan_id", nullable = false)
    private PackageLanguage packageLanguage;

    public PackageLanguage getPackageLanguage() {
        return packageLanguage;
    }

    public void setPackageLanguage(PackageLanguage packageLanguage) {
        this.packageLanguage = packageLanguage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
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

    public PackLanTutorialResource() {

    }

    public PackLanTutorialResource(Timestamp dateAdded, Tutorial tutorial, PackageLanguage packageLanguage) {

        this.dateAdded = dateAdded;
        this.tutorial = tutorial;
        this.packageLanguage = packageLanguage;
    }

    @Override
    public String toString() {
        return "PackLanTutorialResource [id=" + id + ", dateAdded=" + dateAdded + ", status=" + status + "]";
    }

}
