package com.health.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

/**
 * This modal is representation of mappin between category and topic
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
@Table(name = "topic_category")
public class TopicCategoryMapping {

	/**
	 * unique id of object
	 */
	@Id
	@Column(name = "topic_category_id",nullable = false,updatable = false)
	private int topicCategoryId;
	
	/**
	 * boolean value to show/disable
	 */
	@Column(name = "status", nullable = false)
	private boolean status=true;
	
	/**
	 * order value
	 */
	@Column(name = "orderValue")
	private int order = 0;
	
	/**
	 * category 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category cat;
	
	/**
	 * topic
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_id")
	private Topic topic;
	
	@OneToMany(mappedBy = "topicCatId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Question> questions=new HashSet<Question>();
	
	@OneToMany(mappedBy = "topicCatId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ContributorAssignedTutorial> conAssignedTutorial=new HashSet<ContributorAssignedTutorial>();
		
	@OneToMany(mappedBy = "topicCatId", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
	private Set<TrainingTopic> trainingTopic=new HashSet<TrainingTopic>();
	
	@OneToMany(mappedBy = "topicCatId", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
	private Set<Brouchure> brochures=new HashSet<Brouchure>();
	

	public int getTopicCategoryId() {
		return topicCategoryId;
	}

	public void setTopicCategoryId(int topicCategoryId) {
		this.topicCategoryId = topicCategoryId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public TopicCategoryMapping() {
		
	}
	
	public TopicCategoryMapping(int topicCategoryId, boolean status, Category cat, Topic topic,int order) {
		super();
		this.topicCategoryId = topicCategoryId;
		this.status = status;
		this.cat = cat;
		this.topic = topic;
		this.order = order;
	}


	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<ContributorAssignedTutorial> getConAssignedTutorial() {
		return conAssignedTutorial;
	}

	public void setConAssignedTutorial(Set<ContributorAssignedTutorial> conAssignedTutorial) {
		this.conAssignedTutorial = conAssignedTutorial;
	}

	public Set<TrainingTopic> getTrainingTopic() {
		return trainingTopic;
	}

	public void setTrainingTopic(Set<TrainingTopic> trainingTopic) {
		this.trainingTopic = trainingTopic;
	}

	public Set<Brouchure> getBrochures() {
		return brochures;
	}

	public void setBrochures(Set<Brouchure> brochures) {
		this.brochures = brochures;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}


	
	
	
	
}
