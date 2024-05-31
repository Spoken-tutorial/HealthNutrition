package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class District implements Comparable<District>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "district_id", nullable = false)
    int id;

    private String districtName;

    private Timestamp dateAdded;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private State state;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<City> cities = new HashSet<City>();

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Event> events = new HashSet<Event>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    @Override
    public int compareTo(District o) {

        return this.getDistrictName().compareTo(o.getDistrictName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("District [id=").append(id);
        sb.append(", districtName=").append(districtName);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append("]");
        return sb.toString();
    }

}
