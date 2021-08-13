package com.health.model;

import java.sql.Date;
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
 *  Event Object to store Event related data on database
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
public class Event {

	/**
	 * unique id of object
	 */
	@Id
	@Column(name = "event_id",updatable = false,nullable = false)
	private int eventId;

	/**
	 * name of event
	 */
	@Column(name = "event_name", nullable = false,length = 1000)
	private String eventName;

	/**
	 * description of event
	 */
	@Column(name = "description", nullable = false,length = 2000)
	private String description;

	/**
	 * location of event
	 */
	@Column(name = "location", nullable = false)
	private String location;

	/**
	 * coordinator of event
	 */
	@Column(name = "contactPerson", nullable = false)
	private String contactPerson;

	/**
	 * coordinator contact number
	 */
	@Column(name = "contactNumber", nullable = false)
	private long contactNumber;

	/**
	 * E-mail
	 */
	@Column(name = "email", nullable = false)
	private String email;

	/**
	 * relative path of poster
	 */
	@Column(name = "path", nullable = false)
	private String posterPath;

	/**
	 * start date of event
	 */
	@Column(name = "start_Date", nullable = false)
	private Date startDate ;

	
	/**
	 * end date of event
	 */
	@Column(name = "end_date", nullable = false)
	private Date endDate ;

	/**
	 * timestamp of event added
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;

	/**
	 * pincode of location
	 */
	@Column(name = "pincode")
	private int pincode;

	/**
	 * langauge in which event is occuring
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lan_id")
	private Language lan;

	/**
	 * state of location
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="state_id")
	private State state;

	/**
	 * district of location
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="district_id")
	private District district;

	/**
	 * city of location
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="city_id")
	private City city;

	/**
	 * full address
	 */
	@Column(name = "address" ,length = 10000)
	private String address;

	@OneToMany(mappedBy = "traineeInfos" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<TrainingTopic> trainingTopicId = new HashSet<TrainingTopic>();

	/**
	 * user who added
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<TrainingInformation> trainings =new HashSet<TrainingInformation>();

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public Set<TrainingInformation> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<TrainingInformation> trainings) {
		this.trainings = trainings;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public Language getLan() {
		return lan;
	}

	public void setLan(Language lan) {
		this.lan = lan;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<TrainingTopic> getTrainingTopicId() {
		return trainingTopicId;
	}

	public void setTrainingTopicId(Set<TrainingTopic> trainingTopicId) {
		this.trainingTopicId = trainingTopicId;
	}






}
