package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Modal class to record post questionnaire for training conducted
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
public class PostQuestionaire{
	
	/**
	 * unique id of object
	 */
	@Id
	private int id;
	
	/**
	 * relative path of questionnaire
	 */
	private String questionPath;
	
	/**
	 * timestamp of object created
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	/**
	 * training for object created
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Training_id")
	private TrainingInformation traineeInfos;
	
	/**
	 * trainer who added this info.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestionPath() {
		return questionPath;
	}

	public void setQuestionPath(String questionPath) {
		this.questionPath = questionPath;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public TrainingInformation getTraineeInfos() {
		return traineeInfos;
	}

	public void setTraineeInfos(TrainingInformation traineeInfos) {
		this.traineeInfos = traineeInfos;
	}

	public PostQuestionaire(int id, String questionPath, Timestamp dateAdded, TrainingInformation traineeInfos,
			User user) {
		super();
		this.id = id;
		this.questionPath = questionPath;
		this.dateAdded = dateAdded;
		this.traineeInfos = traineeInfos;
		this.user = user;
	}
	
	public PostQuestionaire() {
		
	}
	
	
}