package com.health.model;

import java.sql.Timestamp;
import java.util.Comparator;
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

/**
 * city Object to store city related data on database
 * @author om prkash soni
 * @version 1.0
 */
@Entity
public class City implements Comparable<City>{

	/**
	 * unique id of object
	 */
	@Id
	@Column(name = "city_id", nullable = false)
	private int id;

	/**
	 * timestamp on added 
	 */
	private Timestamp dateAdded;

	/**
	 * name of city
	 */
	private String cityName;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="district_id")
	private District district;


	@OneToMany(mappedBy = "city",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Event> events=new HashSet<Event>();
	
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
		// TODO Auto-generated method stub
		return this.getCityName().compareTo(o.getCityName());
	}


}
