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
import javax.persistence.OneToMany;

@Entity
public class PackageContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int packageId;

    @Column(name = "package_name", nullable = false)
    private String packageName;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @OneToMany(mappedBy = "packageContainer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TutorialWithWeekAndPackage> tutorialsWithWeekAndPack = new HashSet<TutorialWithWeekAndPackage>();

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

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

    public PackageContainer(String packageName, Timestamp dateAdded, boolean status) {

        this.packageName = packageName;
        this.dateAdded = dateAdded;
        this.status = status;
    }

    public PackageContainer(String packageName, Timestamp dateAdded) {

        this.packageName = packageName;
        this.dateAdded = dateAdded;

    }

    public PackageContainer() {

    }

}
