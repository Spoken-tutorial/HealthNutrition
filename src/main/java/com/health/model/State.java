package com.health.model;


import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * this modal object records state object in database
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
public class State implements Comparable<State> {

	/**
	 * unique id of object
	 */
	@Id
	@Column(name = "state_id", nullable = false)
	private int id;

	/**
	 * name of state
	 */
	private String stateName;

	/**
	 * timestamp of object created
	 */
	private Timestamp dateAdded;

	@OneToMany(mappedBy = "state", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<District>  Districts=new HashSet<District>();

	@OneToMany(mappedBy = "state", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Event>  events=new HashSet<Event>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Set<District> getDistricts() {
		return Districts;
	}

	public void setDistricts(Set<District> districts) {
		Districts = districts;
	}

	@Override
	public int compareTo(State o) {
		// TODO Auto-generated method stub
		return this.getStateName().compareTo(o.getStateName());
	}


}
