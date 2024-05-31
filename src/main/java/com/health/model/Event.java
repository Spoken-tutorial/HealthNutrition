package com.health.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "event_id", updatable = false, nullable = false)
    private int eventId;

    @Column(name = "event_name", nullable = false, length = 1000)
    private String eventName;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "contactPerson", nullable = false)
    private String contactPerson;

    @Column(name = "contactNumber", nullable = false)
    private long contactNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "path", nullable = false)
    private String posterPath;

    @Column(name = "start_Date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "pincode")
    private int pincode;

    @ManyToOne
    @JoinColumn(name = "lan_id")
    private Language lan;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "address", length = 10000)
    private String address;

    @OneToMany(mappedBy = "traineeInfos", cascade = CascadeType.ALL)
    private Set<TrainingTopic> trainingTopicId = new HashSet<TrainingTopic>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<TrainingInformation> trainings = new HashSet<TrainingInformation>();

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

    public static Comparator<Event> SortByEventAddedTimeInDesc = new Comparator<Event>() {

        @Override
        public int compare(Event e1, Event e2) {
            return e2.getDateAdded().compareTo(e1.getDateAdded());

        }
    };

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Event [eventId=").append(eventId);
        sb.append(", eventName=").append(eventName);
        sb.append(", description=").append(description);
        sb.append(", location=").append(location);
        sb.append(", contactPerson=").append(contactPerson);
        sb.append(", contactNumber=").append(contactNumber);
        sb.append(", email=").append(email);
        sb.append(", posterPath=").append(posterPath);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", pincode=").append(pincode);
        sb.append("]");
        return sb.toString();
    }

}
