package com.health.service;

import java.sql.Date;
import java.util.List;

import com.health.model.Event;
import com.health.model.User;


/**
 * This interface has all the method declaration related to Brochure object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface EventService {

	/**
	 * Find All the Event object from database
	 * @return list of Event object
	 */
	List<Event> findAll();    // in use

	/**
	 * delete event object from database
	 * @param id int value
	 */
	void deleteProduct(Integer id);

	/**
	 * Find Event object given id value
	 * @param id int value
	 * @return Event object
	 */
	Event findById(int id);   //  in use

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	int getNewEventId();       // in use

	/**
	 * Persist Event object into database
	 * @param event Event Object
	 */
	void save(Event event);   // in use

	/**
	 * Find List of Event object given user object
	 * @param usr user object
	 * @return List of event object
	 */
	List<Event> findByUser(User usr);



}
