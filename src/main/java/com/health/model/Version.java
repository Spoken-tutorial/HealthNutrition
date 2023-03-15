package com.health.model;

import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Version {
	
	@Id
	int verId;
	
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	@Column(name = "Image_path", nullable = false)
	private String versionPosterPath;
	
	@ManyToOne
	@JoinColumn(name = "bro_id")
	private Brouchure brouchure;
	
	@Column(name="Brouchure_Version")
	int broVersion;
	
	

	public Version(int verId, Timestamp dateAdded, String versionPosterPath, Brouchure brouchure, int broVersion) {
		super();
		this.verId = verId;
		this.dateAdded = dateAdded;
		this.versionPosterPath = versionPosterPath;
		this.brouchure = brouchure;
		this.broVersion = broVersion;
	}

	public int getBroVersion() {
		return broVersion;
	}

	public void setBroVersion(int broVersion) {
		this.broVersion = broVersion;
	}

	public Version() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Version(int verId, Timestamp dateAdded, String versionPosterPath, Brouchure brouchure) {
		super();
		this.verId = verId;
		this.dateAdded = dateAdded;
		this.versionPosterPath = versionPosterPath;
		this.brouchure = brouchure;
	}

	
	 public static Comparator<Version> SortByBroVersionInDesc = new Comparator<Version>() {
		  
	        // Method
	        public int compare(Version v1, Version v2) {
	        	
	        	if(v1.getBroVersion() < v2.getBroVersion()) {
	        	  return 1;
	          }
	          else {
	        	  return -1;
	          }
	            
	        }
	    };
	    
	
	public Brouchure getBrouchure() {
		return brouchure;
	}

	public void setBrouchure(Brouchure brouchure) {
		this.brouchure = brouchure;
	}

	public int getVerId() {
		return verId;
	}

	public void setVerId(int verId) {
		this.verId = verId;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getVersionPosterPath() {
		return versionPosterPath;
	}

	public void setVersionPosterPath(String versionPosterPath) {
		this.versionPosterPath = versionPosterPath;
	}
	
	

}
