package com.health.model;

import java.sql.Timestamp;
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
public class PackLanWeekMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pack_lan_week_id", nullable = false, updatable = false)
    private int packLanWeekId;
    private Timestamp dateAdded;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "packageLan_ID")
    private PackageLanMapping packageLan;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "week_id")
    private Week week;

    @OneToMany(mappedBy = "packLanWeek", cascade = CascadeType.ALL)
    private Set<NptelTutorial> nptelTutorials = new HashSet<NptelTutorial>();

    public int getPackLanWeekId() {
        return packLanWeekId;
    }

    public void setPackLanWeekId(int packLanWeekId) {
        this.packLanWeekId = packLanWeekId;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public PackageLanMapping getPackageLan() {
        return packageLan;
    }

    public void setPackageLan(PackageLanMapping packageLan) {
        this.packageLan = packageLan;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Set<NptelTutorial> getNptelTutorials() {
        return nptelTutorials;
    }

    public void setNptelTutorials(Set<NptelTutorial> nptelTutorials) {
        this.nptelTutorials = nptelTutorials;
    }

    public PackLanWeekMapping() {
        super();

    }

}
