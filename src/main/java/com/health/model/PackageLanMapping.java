package com.health.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PackageLanMapping implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "package_lan_id", nullable = false, updatable = false)
    private int packageLanId;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageEntity package1;

    @ManyToOne
    @JoinColumn(name = "lan_id")
    private Language lan;

    @OneToMany(mappedBy = "packageLan", cascade = CascadeType.ALL)
    private Set<PackLanWeekMapping> packLanWeekMappings = new HashSet<PackLanWeekMapping>();

    public int getPackageLanId() {
        return packageLanId;
    }

    public void setPackageLanId(int packageLanId) {
        this.packageLanId = packageLanId;
    }

    public Set<PackLanWeekMapping> getPackLanWeekMappings() {
        return packLanWeekMappings;
    }

    public void setPackLanWeekMappings(Set<PackLanWeekMapping> packLanWeekMappings) {
        this.packLanWeekMappings = packLanWeekMappings;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PackageEntity getPackage1() {
        return package1;
    }

    public void setPackage1(PackageEntity package1) {
        this.package1 = package1;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public PackageLanMapping() {
        super();

    }

}
