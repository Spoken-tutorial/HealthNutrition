
package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "state_district_mapping")
public class StateDistrictMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_dis_id", nullable = false, updatable = false)
    private int stateDisId;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    // @OneToMany(mappedBy = "topicLanMapping", cascade = CascadeType.ALL)
    // private Set<TrainingResource> trainingResources = new
    // HashSet<TrainingResource>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStateDisId() {
        return stateDisId;
    }

    public void setStateDisId(int stateDisId) {
        this.stateDisId = stateDisId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public static Comparator<StateDistrictMapping> SortByStateName = new Comparator<StateDistrictMapping>() {

        @Override
        public int compare(StateDistrictMapping sd1, StateDistrictMapping sd2) {

            return sd1.getState().getStateName().compareTo(sd2.getState().getStateName());

        }
    };

}
