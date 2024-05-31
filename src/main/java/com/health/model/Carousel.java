package com.health.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String posterPath;

    private boolean showOnHomepage = false;

    @Column(length = 1000)
    private String eventName;

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
