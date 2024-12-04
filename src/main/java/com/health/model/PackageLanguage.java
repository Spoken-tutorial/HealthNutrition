package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class PackageLanguage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int packageLanId;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private PackageContainer packageContainer;

    @ManyToOne
    @JoinColumn(name = "lan_id", nullable = false)
    private Language lan;

    @OneToMany(mappedBy = "packageLanguage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TutorialWithWeekAndPackage> tutorialsWithWeekAndPack = new HashSet<TutorialWithWeekAndPackage>();

    @OneToMany(mappedBy = "packageLanguage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PackLanTutorialResource> PackLanTutorialResource = new HashSet<PackLanTutorialResource>();

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public Set<TutorialWithWeekAndPackage> getTutorialsWithWeekAndPack() {
        return tutorialsWithWeekAndPack;
    }

    public void setTutorialsWithWeekAndPack(Set<TutorialWithWeekAndPackage> tutorialsWithWeekAndPack) {
        this.tutorialsWithWeekAndPack = tutorialsWithWeekAndPack;
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

    public int getPackageLanId() {
        return packageLanId;
    }

    public void setPackageLanId(int packageLanId) {
        this.packageLanId = packageLanId;
    }

    public PackageContainer getPackageContainer() {
        return packageContainer;
    }

    public void setPackageContainer(PackageContainer packageContainer) {
        this.packageContainer = packageContainer;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public PackageLanguage() {

    }

    public List<WeekTitleVideo> getAllWeekTitle() {
        Set<WeekTitleVideo> weektitleSet = new HashSet<>();
        for (TutorialWithWeekAndPackage temp : tutorialsWithWeekAndPack) {

            weektitleSet.add(temp.getWeekTitle());
        }
        return new ArrayList<WeekTitleVideo>(weektitleSet);

    }

    public Set<PackLanTutorialResource> getPackLanTutorialResource() {
        return PackLanTutorialResource;
    }

    public void setPackLanTutorialResource(Set<PackLanTutorialResource> packLanTutorialResource) {
        PackLanTutorialResource = packLanTutorialResource;
    }

    public PackageLanguage(Timestamp dateAdded, PackageContainer packageContainer, Language lan) {
        super();
        this.dateAdded = dateAdded;
        this.packageContainer = packageContainer;
        this.lan = lan;
    }

    public PackageLanguage(Timestamp dateAdded, PackageContainer packageContainer, Language lan,
            Set<TutorialWithWeekAndPackage> tutorialsWithWeekAndPack) {
        super();
        this.dateAdded = dateAdded;
        this.packageContainer = packageContainer;
        this.lan = lan;
        this.tutorialsWithWeekAndPack = tutorialsWithWeekAndPack;
    }

}
