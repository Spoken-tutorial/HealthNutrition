package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class ResearchPaper {

    @Id
    private int id;

    private String title;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "Description", nullable = false, length = 2000)
    private String description;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "added_queue", nullable = false)
    private boolean addedQueue = false;

    @Column(name = "researchPaperVisit")
    @ColumnDefault("0")
    private int researchPaperVisit = 0;

    private String ResearchPaperPath;

    private boolean showOnHomepage = false;

    public int getResearchPaperVisit() {
        return researchPaperVisit;
    }

    public void setResearchPaperVisit(int researchPaperVisit) {
        this.researchPaperVisit = researchPaperVisit;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public int getId() {
        return id;
    }

    public boolean isAddedQueue() {
        return addedQueue;
    }

    public void setAddedQueue(boolean addedQueue) {
        this.addedQueue = addedQueue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResearchPaperPath() {
        return ResearchPaperPath;
    }

    public void setResearchPaperPath(String researchPaperPath) {
        ResearchPaperPath = researchPaperPath;
    }

    public boolean isShowOnHomepage() {
        return showOnHomepage;
    }

    public void setShowOnHomepage(boolean showOnHomepage) {
        this.showOnHomepage = showOnHomepage;
    }

    public ResearchPaper() {

    }

    public ResearchPaper(int id, String title, Timestamp dateAdded, String description, String researchPaperPath,
            boolean showOnHomepage) {
        super();
        this.id = id;
        this.title = title;
        this.dateAdded = dateAdded;
        this.description = description;
        ResearchPaperPath = researchPaperPath;
        this.showOnHomepage = showOnHomepage;
    }

    public ResearchPaper(String title, Timestamp dateAdded, String description, String researchPaperPath,
            boolean showOnHomepage) {
        super();
        this.title = title;
        this.dateAdded = dateAdded;
        this.description = description;
        ResearchPaperPath = researchPaperPath;
        this.showOnHomepage = showOnHomepage;
    }

    public ResearchPaper(int id, String title, Timestamp dateAdded, String researchPaperPath, boolean showOnHomepage) {
        super();
        this.id = id;
        this.title = title;
        this.dateAdded = dateAdded;
        ResearchPaperPath = researchPaperPath;
        this.showOnHomepage = showOnHomepage;
    }

    public ResearchPaper(String title, Timestamp dateAdded, String researchPaperPath, boolean showOnHomepage) {
        super();
        this.title = title;
        this.dateAdded = dateAdded;
        ResearchPaperPath = researchPaperPath;
        this.showOnHomepage = showOnHomepage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResearchPaper [id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", description=").append(description);
        sb.append(", thumbnailPath=").append(thumbnailPath);
        sb.append("]");
        return sb.toString();
    }

}
