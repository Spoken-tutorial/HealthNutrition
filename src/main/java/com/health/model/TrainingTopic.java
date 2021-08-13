package com.health.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Training topic information object saved in database
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
public class TrainingTopic {

	/**
	 * unique id of object
	 */
	@Id
	private int trainingTopicId;

	/**
	 * topic category mapping
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="topicCat_id")
	private TopicCategoryMapping topicCatId;

	/**
	 * training object
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="Training_id")
	private TrainingInformation traineeInfos;

	/**
	 * event object
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="Event_id")
	private Event event;

	public int getTrainingTopicId() {
		return trainingTopicId;
	}

	public void setTrainingTopicId(int trainingTopicId) {
		this.trainingTopicId = trainingTopicId;
	}

	public TopicCategoryMapping getTopicCatId() {
		return topicCatId;
	}

	public void setTopicCatId(TopicCategoryMapping topicCatId) {
		this.topicCatId = topicCatId;
	}

	public TrainingInformation getTraineeInfos() {
		return traineeInfos;
	}

	public void setTraineeInfos(TrainingInformation traineeInfos) {
		this.traineeInfos = traineeInfos;
	}


	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public TrainingTopic(int trainingTopicId, TopicCategoryMapping topicCatId, TrainingInformation traineeInfos, Event event) {
		super();
		this.trainingTopicId = trainingTopicId;
		this.topicCatId = topicCatId;
		this.traineeInfos = traineeInfos;
		this.event = event;
	}
	public TrainingTopic(int trainingTopicId, TopicCategoryMapping topicCatId, TrainingInformation traineeInfos) {
		super();
		this.trainingTopicId = trainingTopicId;
		this.topicCatId = topicCatId;
		this.traineeInfos = traineeInfos;

	}
	public TrainingTopic(int trainingTopicId, TopicCategoryMapping topicCatId, Event event) {
		super();
		this.trainingTopicId = trainingTopicId;
		this.topicCatId = topicCatId;
		this.event = event;
	}

	public TrainingTopic() {

	}
}
