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
public class City implements Comparable<City>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "city_id", nullable = false)
    private int id;

    private Timestamp dateAdded;

    private String cityName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    private District district;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<Event>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @Override
    public int compareTo(City o) {

        return this.getCityName().compareTo(o.getCityName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("City [id=").append(id);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", cityName=").append(cityName);
        sb.append("]");
        return sb.toString();
    }

}
