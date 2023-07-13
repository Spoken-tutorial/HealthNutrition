package com.health.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ResearchPaper {
	
	@Id
	private int id;
	
	private String title;
	
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	@Column(name = "Description", nullable = false,length = 2000)
	private String description;
	
	@Column(name = "thumbnail_path")
	private String thumbnailPath;

	/**
	 * Location to store brochure 
	 */
	private String ResearchPaperPath;
	
	/**
	 * Boolean value to show on Homepage or not
	 */
	private boolean showOnHomepage=false;
	
	

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public int getId() {
		return id;
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
	
	


}