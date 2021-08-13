package com.health.model;

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
import javax.persistence.Table;

import com.health.domain.security.UserRole;

/**
 * category Object to store category related data on database
 * @author om prakash soni
 * @version 1.0
 */
@Entity
@Table(name = "category")
public class Category implements Comparable<Category>{

	/**
	 * unique id to identify object
	 */
	@Id
	@Column(name = "category_id", nullable = false, updatable = false)
	private int categoryId;
	
	/**
	 * category name
	 */
	@Column(name = "category_name", nullable = false)
	private String catName;
	
	/**
	 * Timestamp on added 
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	/**
	 * boolean value to mark visible/invisible in application
	 */
	@Column(name = "status", nullable = false)
	private boolean status=true;
	
	/**
	 * relative path of image stored
	 */
	@Column(name = "Image_path", nullable = false)
	private String posterPath;
	
	/**
	 * description of category
	 */
	@Column(name = "Description", nullable = false,length = 2000)
	private String description;
	
	/**
	 * user who created it
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<TopicCategoryMapping> topicCategoryMap=new HashSet<TopicCategoryMapping>();
	
	@OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserRole> userRoles=new HashSet<UserRole>();
	

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<TopicCategoryMapping> getTopicCategoryMap() {
		return topicCategoryMap;
	}

	public void setTopicCategoryMap(Set<TopicCategoryMapping> topicCategoryMap) {
		this.topicCategoryMap = topicCategoryMap;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public int compareTo(Category o) {
		// TODO Auto-generated method stub
		System.out.println(o.getCatName());
		return this.getCatName().compareTo(o.getCatName());
		
	}


}
