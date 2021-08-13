package com.health.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

/**
 * Comment Object to store Comment related data on database
 * @author om prakash soni
 * @version 1.0
 */
@Entity
public class Comment{
	
	/**
	 * unique id of object
	 */
	@Id
	private int commentId;
	
	/**
	 * actual comment
	 */
	@Column(length = 2000)
	private String comment;
	
	/**
	 * timestamp of comment
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	/**
	 * component for which comment is made
	 */
	private String type;
	
	/**
	 * role name of user who made comment
	 */
	private String roleName;
	
	/**
	 * user who made comment
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * tutorial for which comment is made
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Tutorial_id")
	private Tutorial tutorialInfos;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp timestamp) {
		this.dateAdded = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tutorial getTutorialInfos() {
		return tutorialInfos;
	}

	public void setTutorialInfos(Tutorial tutorialInfos) {
		this.tutorialInfos = tutorialInfos;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

	
}
