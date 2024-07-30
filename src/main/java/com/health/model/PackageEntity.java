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
import javax.persistence.OneToMany;

@Entity
public class PackageEntity implements Comparable<PackageEntity>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "package_id", nullable = false, updatable = false)
    private int packageId;

    @Column(name = "package_name", nullable = false, unique = true)
    private String packageName;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @OneToMany(mappedBy = "package1", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PackageLanMapping> packageLanMap = new HashSet<PackageLanMapping>();

    @Column(name = "status", nullable = false)
    private boolean status = true;

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Set<PackageLanMapping> getPackageLanMap() {
        return packageLanMap;
    }

    public void setPackageLanMap(Set<PackageLanMapping> packageLanMap) {
        this.packageLanMap = packageLanMap;
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PackageEntity() {
        super();

    }

    public PackageEntity(int packageId, String packageName, Timestamp dateAdded, boolean status) {
        super();
        this.packageId = packageId;
        this.packageName = packageName;
        this.dateAdded = dateAdded;
        this.status = status;
    }

    public static Comparator<PackageEntity> SortByUploadTime = new Comparator<PackageEntity>() {

        @Override
        public int compare(PackageEntity p1, PackageEntity p2) {
            return p2.getDateAdded().compareTo(p1.getDateAdded());

        }
    };

    @Override
    public int compareTo(PackageEntity o) {

        return this.getPackageName().compareTo(o.getPackageName());
    }

}
