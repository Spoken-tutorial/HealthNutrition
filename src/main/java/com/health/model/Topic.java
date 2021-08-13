
package com.health.model;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
  
	  import javax.persistence.CascadeType;
	  import javax.persistence.Column;
	import javax.persistence.Entity; import
	  javax.persistence.FetchType; import javax.persistence.GeneratedValue; import
	  javax.persistence.GenerationType; import javax.persistence.Id;
	import javax.persistence.JoinColumn;
	import javax.persistence.ManyToOne;
	import
	  javax.persistence.OneToMany;
	import javax.persistence.OneToOne;
	import javax.persistence.Table;

	/**
	 * This modal add topic object into database  
	 * @author om prakash soni
	 * @version 1.0
	 *
	 */
@Entity
@Table(name="topic")
public class Topic implements Comparable<Topic>{
	
	/**
	 * unique id
	 */
	@Id
	@Column(name="topic_id",nullable = false, updatable = false)
	private int topicId;
	
	/**
	 * topic name
	 */
	@Column(name="topic_name",nullable = false)
	private String topicName;
	
	/**
	 * timestamp of object created
	 */
	@Column(name = "date_added", nullable = false)
	private Timestamp dateAdded;
	
	/**
	 * boolean value to show/disable on application
	 */
	@Column(name = "status", nullable = false)
	private boolean status=true;
	
	/**
	 * user who add this
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<TopicCategoryMapping> topicCategoryMap=new HashSet<TopicCategoryMapping>();
	

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<TopicCategoryMapping> getTopicCategoryMap() {
		return topicCategoryMap;
	}

	public void setTopicCategoryMap(Set<TopicCategoryMapping> topicCategoryMap) {
		this.topicCategoryMap = topicCategoryMap;
	}

	@Override
	public int compareTo(Topic o) {
		// TODO Auto-generated method stub
		return this.getTopicName().compareTo(o.getTopicName());
	}

	  
	  

			
		  
		  
		  
}
 