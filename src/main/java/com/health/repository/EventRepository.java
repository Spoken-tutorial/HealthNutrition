package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Event;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Event object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface EventRepository extends  CrudRepository<Event, Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(eventId) from Event")
	int getNewId();

	/**
	 * Find List of Event object given user object
	 * @param usr user object
	 * @return List of event object
	 */
	@Query("from Event e where e.user=?1")
	List<Event> findByuser(User usr);

	/**
	 * Find All Event object 
	 * @return List of Event object
	 */
	@Query("from Event e order by e.startDate desc")  // fetching list of event
	List<Event> getAllEvent();

}
