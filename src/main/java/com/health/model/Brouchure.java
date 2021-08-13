package com.health.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Brochure Object to store brochure related data on database 
 * @author Om Prakash Soni
 * @version 1.0
 *
 */
@Entity
public class Brouchure {

	/**
	 * unique brochure id 
	 */
	@Id
	private int id;
	
	/**
	 * Location to store brochure 
	 */
	private String posterPath;
	
	/**
	 * Boolean value to show on Homepage or not
	 */
	private boolean showOnHomepage=false;
	
	/**
	 * Language mapped object it is associated with
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lan_id")
	private Language lan;
	
	/**
	 * Topic category Mapped object to which it belongs
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topicCat_id")
	private TopicCategoryMapping topicCatId;

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

	public boolean isShowOnHomepage() {
		return showOnHomepage;
	}

	public void setShowOnHomepage(boolean showOnHomepage) {
		this.showOnHomepage = showOnHomepage;
	}
	
	
	
}
