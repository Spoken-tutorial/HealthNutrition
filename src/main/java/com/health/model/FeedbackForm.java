package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * feedback Object to store feedback related data on database
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
@Table(name = "feedbackForm")
public class FeedbackForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * unique id of object
     */
    @Id
    private int id;

    /**
     * name of person
     */
    private String name;

    /**
     * e-mail of person
     */
    private String email;

    /**
     * query
     */
    @Column(length = 2000)
    private String message;

    /**
     * timestamp on which feedback is given
     */
    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String mastertTrainerName) {
        this.name = mastertTrainerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FeedbackForm [id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", email=").append(email);
        sb.append(", message=").append(message);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append("]");
        return sb.toString();
    }

}
