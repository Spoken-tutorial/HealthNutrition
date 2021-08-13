package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Consultant Object to store consultant related data on database
 * @author om prakash soni
 * @version 1.0
 */
@Entity
public class Consultant{
	
	/**
	 * unique id of object
	 */
	@Id
	@Column(name = "consult_id",updatable = false,nullable = false)
	private int consultantId;
	
	/**
	 * description of consultant
	 */
	@Column(name = "description", nullable = false,length = 2000)
	private String description;
	
	/**
	 * timestamp of which consultant added
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	/**
	 * boolean to make visible/invisible on application
	 */
	@Column(name = "showonhomepage", nullable = false)
	private boolean onHome = false;
	
	/**
	 * user who added
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public int getConsultantId() {
		return consultantId;
	}

	public void setConsultantId(int consultantId) {
		this.consultantId = consultantId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public boolean isOnHome() {
		return onHome;
	}

	public void setOnHome(boolean onHome) {
		this.onHome = onHome;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
