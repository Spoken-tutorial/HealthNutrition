package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * userAssignedTutorial Object to store userAssignedTutorial related data on database
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
@Table(name = "UserOnTutorialAssigned")
public class ContributorAssignedMultiUserTutorial implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * unique id of object
	 */
	@Id
	private int id;
	
	/**
	 * timestamp of object created
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	/**
	 * user assigned for tutorial
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="user_assigned")
	private User user;
	
	/**
	 * tutorial info
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conAssignedTutorial")
	private ContributorAssignedTutorial conAssignedTutorial;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ContributorAssignedTutorial getConAssignedTutorial() {
		return conAssignedTutorial;
	}

	public void setConAssignedTutorial(ContributorAssignedTutorial conAssignedTutorial) {
		this.conAssignedTutorial = conAssignedTutorial;
	}
	
	public ContributorAssignedMultiUserTutorial() {
		
	}

	public ContributorAssignedMultiUserTutorial(int id, Timestamp dateAdded, User user,
			ContributorAssignedTutorial conAssignedTutorial) {
		super();
		this.id = id;
		this.dateAdded = dateAdded;
		this.user = user;
		this.conAssignedTutorial = conAssignedTutorial;
	}
	
	
	
}
