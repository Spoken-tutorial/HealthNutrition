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

/**
 * This modal add Training information to database
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
public class TrainingInformation{

	/**
	 * unique id of object
	 */
	@Id
	private int trainingId;

	/**
	 * number of participant
	 */
	@Column(name = "totalParticipant")
	private int totalParticipant;

	/**
	 * relative path of photos of training
	 */
	@Column(name= "PosterPath")
	private String posterPath;

	/**
	 * language
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="state_id")
	private Language lan;

	/**
	 * event name
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="event_id")
	private Event event;

	/**
	 * user who added this
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;

	/**
	 * Timestaamp when object created
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;

	/**
	 * address
	 */
	@Column(name = "address" ,length = 10000)
	private String address;

	@OneToMany(mappedBy = "traineeInfos" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TraineeInformation> traineeInfos= new HashSet<TraineeInformation>();

	@OneToMany(mappedBy = "traineeInfos" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TrainingTopic> trainingTopicId = new HashSet<TrainingTopic>();

	@OneToMany(mappedBy = "traineeInfos" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<FeedbackMasterTrainer> masterTrainerFeedback = new HashSet<FeedbackMasterTrainer>();

	@OneToMany(mappedBy = "traineeInfos" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PostQuestionaire> postQuestions = new HashSet<PostQuestionaire>();

	@OneToMany(mappedBy = "traineeInfos" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Testimonial> testimonials = new HashSet<Testimonial>();

	public int getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}

	public int getTotalParticipant() {
		return totalParticipant;
	}

	public void setTotalParticipant(int totalParticipant) {
		this.totalParticipant = totalParticipant;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public Set<TrainingTopic> getTrainingTopicId() {
		return trainingTopicId;
	}

	public void setTrainingTopicId(Set<TrainingTopic> trainingTopicId) {
		this.trainingTopicId = trainingTopicId;
	}

	public Language getLan() {
		return lan;
	}

	public void setLan(Language lan) {
		this.lan = lan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<TraineeInformation> getTraineeInfos() {
		return traineeInfos;
	}

	public void setTraineeInfos(Set<TraineeInformation> traineeInfos) {
		this.traineeInfos = traineeInfos;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Set<FeedbackMasterTrainer> getMasterTrainerFeedback() {
		return masterTrainerFeedback;
	}

	public void setMasterTrainerFeedback(Set<FeedbackMasterTrainer> masterTrainerFeedback) {
		this.masterTrainerFeedback = masterTrainerFeedback;
	}

	public Set<PostQuestionaire> getPostQuestions() {
		return postQuestions;
	}

	public void setPostQuestions(Set<PostQuestionaire> postQuestions) {
		this.postQuestions = postQuestions;
	}




}