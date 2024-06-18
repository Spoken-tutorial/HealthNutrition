
package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Week implements Comparable<Week>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "week_id", nullable = false, updatable = false)
    private int weekId;

    @Column(name = "week_name", nullable = false, unique = true)
    private String weekName;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @OneToMany(mappedBy = "week", cascade = CascadeType.ALL)
    private Set<PackLanWeekMapping> packLanWeekMappings = new HashSet<PackLanWeekMapping>();

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Set<PackLanWeekMapping> getPackLanWeekMappings() {
        return packLanWeekMappings;
    }

    public void setPackLanWeekMappings(Set<PackLanWeekMapping> packLanWeekMappings) {
        this.packLanWeekMappings = packLanWeekMappings;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Week() {
        super();

    }

    public Week(int weekId, String weekName, Timestamp dateAdded, boolean status) {
        super();
        this.weekId = weekId;
        this.weekName = weekName;
        this.dateAdded = dateAdded;
        this.status = status;
    }

    public static Comparator<Week> SortByUploadTime = new Comparator<Week>() {

        @Override
        public int compare(Week p1, Week p2) {
            return p2.getDateAdded().compareTo(p1.getDateAdded());

        }
    };

    @Override
    public int compareTo(Week o) {

        return this.getWeekName().compareTo(o.getWeekName());
    }

}
