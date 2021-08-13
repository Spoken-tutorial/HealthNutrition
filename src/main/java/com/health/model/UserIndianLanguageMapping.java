package com.health.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * this modal is used for master trainer
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
public class UserIndianLanguageMapping {

	/**
	 * unique id
	 */
	@Id
	private int id;
	
	/**
	 * redaing check 
	 */
	private boolean reading = false;
	
	/**
	 * write check
	 */
	private boolean writing = false;
	
	/**
	 * speak check
	 */
	private boolean speaking = false;
	
	/**
	 * user object
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lan_id")
	private IndianLanguage indianlan;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isRead() {
		return reading;
	}

	public void setRead(boolean read) {
		this.reading = read;
	}

	public boolean isWrite() {
		return writing;
	}

	public void setWrite(boolean write) {
		this.writing = write;
	}

	public boolean isSpeak() {
		return speaking;
	}

	public void setSpeak(boolean speak) {
		this.speaking = speak;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IndianLanguage getIndianlan() {
		return indianlan;
	}

	public void setIndianlan(IndianLanguage indianlan) {
		this.indianlan = indianlan;
	}

	public UserIndianLanguageMapping(int id, boolean read, boolean write, boolean speak, User user,
			IndianLanguage indianlan) {
		super();
		this.id = id;
		this.reading = read;
		this.writing = write;
		this.speaking = speak;
		this.user = user;
		this.indianlan = indianlan;
	}
	
	public UserIndianLanguageMapping() {
		
	}
	
}
