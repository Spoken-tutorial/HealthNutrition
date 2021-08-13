package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Organization role for master trainer
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
public class OrganizationRole implements Comparable<OrganizationRole> {

	/**
	 * unique id of object
	 */
	@Id
	@Column(name = "org_role_id",updatable = false,nullable = false)
	private int roleId;

	/**
	 * role name
	 */
	@Column(nullable = false)
	private String role;

	/**
	 * timestamp of object created
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Override
	public int compareTo(OrganizationRole o) {
		// TODO Auto-generated method stub
		return this.getRole().compareTo(o.getRole());
	}


}
