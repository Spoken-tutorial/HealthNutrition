package com.health.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Carousel Object to store carousel related data on database
 * 
 * @author Om Prakash Soni
 * @version 1.0
 */
@Entity
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * unique id to identify object
     */
    @Id
    private int id;

    /**
     * relative path of image stored specific to object
     */
    private String posterPath;

    /**
     * Boolean value to show on Homepage or not
     */
    private boolean showOnHomepage = false;

    /**
     * name of event
     */
    @Column(length = 1000)
    private String eventName;

    /**
     * description of event
     */
    @Column(length = 2000)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isShowOnHomepage() {
        return showOnHomepage;
    }

    public void setShowOnHomepage(boolean showOnHomepage) {
        this.showOnHomepage = showOnHomepage;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Carousel [id=").append(id);
        sb.append(", posterPath=").append(posterPath);
        sb.append(", showOnHomepage=").append(showOnHomepage);
        sb.append(", eventName=").append(eventName);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }

}
