package com.health.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * This modal class to record the question
 * @author om prakash soni
 * @version 1.0
 */
@Entity
public class Question{
	
	/**
	 * unique id of object
	 */
	@Id
	@Column(name = "question_id",updatable = false,nullable = false)
	private int questionId;
	
	/**
	 * relative path of question
	 */
	@Column(name = "question_path",nullable = false)
	private String questionPath;
	
	/**
	 * timestamp of object created
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	/**
	 * user who added question
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * language for question
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lan_id")
	private Language lan;
	
	/**
	 * category and topic of question
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_cat_id")
	private TopicCategoryMapping topicCatId;
	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Language getLan() {
		return lan;
	}

	public void setLan(Language lan) {
		this.lan = lan;
	}

	public TopicCategoryMapping getTopicCatId() {
		return topicCatId;
	}

	public void setTopicCatId(TopicCategoryMapping topicCatId) {
		this.topicCatId = topicCatId;
	}

	
	
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO) 
//	
//	private int id;
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public Tutorial getTutorial() {
//		return tutorial;
//	}
//
//	public void setTutorial(Tutorial tutorial) {
//		this.tutorial = tutorial;
//	}
//
//	private String Quetionpath;
//
//	
//	@ManyToOne		
//	private Tutorial tutorial;
//	
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name="topic_id")
//	private topic topic;
//	
//	
//	public Question() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name="cat_id")
//	private Category category;
//	
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name="lan_id")
//	private language lan;
//	
//
//	public String getQuetionpath() {
//		return Quetionpath;
//	}
//
//	public void setQuetionpath(String quetionpath){
//		
//		Quetionpath = quetionpath;
//	
//	}
//	
//	public Question(com.health.model.topic topic) {
//		super();
//		this.topic = topic;
//	}
//
//	public Question(String quetionpath, Tutorial tutorial) {
//		super();
//		Quetionpath = quetionpath;
//		this.tutorial = tutorial;
//	}
//
//	public topic getTopic() {
//		return topic;
//	}
//
//	public void setTopic(topic topic) {
//		this.topic = topic;
//	}
//
//	public Category getCategory() {
//		return category;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}
//
//	public language getLan() {
//		return lan;
//	}
//
//	public void setLan(language lan) {
//		this.lan = lan;
//	}
//
//	
//
//	/*
//	 * @OneToMany(mappedBy = "question",
//	 * cascade=CascadeType.ALL,fetch=FetchType.EAGER) private Set<category_Tutorial>
//	 * category_Tutorials=new HashSet<>();
//	 */
//	
//	
//	/*
//	 * 
//	 * public Set<category_Tutorial> getCategory_Tutorials() { return
//	 * category_Tutorials; }
//	 * 
//	 * public void setCategory_Tutorials(Set<category_Tutorial> category_Tutorials)
//	 * { this.category_Tutorials = category_Tutorials; }
//	 * 
//	 */
	
}
