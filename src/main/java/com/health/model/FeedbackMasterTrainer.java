package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * feedbackMasterTrainer object to store data in database
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
public class FeedbackMasterTrainer {

	/**
	 * unique id of object
	 */
	@Id
	private int TrainerFeedId;

	/**
	 * description
	 */
	@Column(length = 2000)
	private String description;

	/**
	 * timestamp feedback given
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;

	/**
	 * relative path where feedback is given
	 */
	private String path;

	/**
	 * feedback given for training
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Training_id")
	private TrainingInformation traineeInfos;

	/**
	 * user who given feedback
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	public int getId() {
		return TrainerFeedId;
	}

	public void setId(int id) {
		this.TrainerFeedId = id;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public TrainingInformation getTraineeInfos() {
		return traineeInfos;
	}

	public void setTraineeInfos(TrainingInformation traineeInfos) {
		this.traineeInfos = traineeInfos;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FeedbackMasterTrainer(int trainerFeedId, String description, Timestamp dateAdded,
			String path, TrainingInformation traineeInfos, User user) {
		super();
		TrainerFeedId = trainerFeedId;
		this.description = description;
		this.dateAdded = dateAdded;
		this.path = path;
		this.traineeInfos = traineeInfos;
		this.user = user;
	}

	public FeedbackMasterTrainer() {

	}


}