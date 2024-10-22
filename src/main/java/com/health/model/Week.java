package com.health.model;

import java.io.Serializable;
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
public class Week implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int weekId;

    @Column(name = "week_number", nullable = false)
    private int weekNumber;

    @OneToMany(mappedBy = "week", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WeekTitle> weekTitles = new HashSet<WeekTitle>();

    public int getWeekId() {
        return weekId;
    }

    public String getWeekName() {
        return "Week " + weekNumber;
    }

    public Set<WeekTitle> getWeekTitles() {
        return weekTitles;
    }

    public void setWeekTitles(Set<WeekTitle> weekTitles) {
        this.weekTitles = weekTitles;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public Week() {

    }

}
