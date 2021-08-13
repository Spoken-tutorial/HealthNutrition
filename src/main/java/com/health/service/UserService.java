package com.health.service;

import java.util.List;
import java.util.Set;


import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Consultant;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Event;
import com.health.model.Language;
import com.health.model.Question;
import com.health.model.Testimonial;
import com.health.model.Topic;
import com.health.model.User;
import com.health.model.UserIndianLanguageMapping;

/**
 * This interface has all the method declaration related to User object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface UserService {
	
	/**
	 * Find User object given Token
	 * @param token String object
	 * @return user object
	 */
	User findBytoken(String token);
	
	/**
	 * Find User object given username
	 * @param username String object
	 * @return user object
	 */
	User findByUsername(String username);   // in use
	
	/**
	 * Find User object given email
	 * @param email String object
	 * @return user object
	 */
	User findByEmail (String email);         // in use
	
	/**
	 * This Method is to create User in database
	 * @param user user object
	 * @param userRoles set of UserRole object
	 * @return User object
	 * @throws Exception
	 */
	User createUser(User user, Set<UserRole> userRoles) throws Exception;    // in use 
	
	/**
	 * Persist User object into database
	 * @param user User object
	 */
	User save(User user);      // in use
	
	/**
	 * Find the next unique id for User object
	 * @return primitive integer value
	 */
	long getNewId();            // in use
	
	/**
	 * Add User Entry to category field
	 * @param usr user object 
	 * @param categories Set of category object
	 * @return user object
	 */
	User addUserToCategory(User usr,Set<Category> categories);   // in use
	
	/**
	 * Add User Entry to Topic field
	 * @param usr user object 
	 * @param topics Set of Topic object
	 * @return user object
	 */
	User addUserToTopic(User usr,Set<Topic> topics);              // in use
	
	/**
	 * Add User Entry to Language field
	 * @param usr user object 
	 * @param languages Set of Language object
	 * @return user object
	 */
	User addUserToLanguage(User usr,Set<Language> languages);       // in use
	
	/**
	 * Add User Entry to Question field
	 * @param usr user object 
	 * @param questions Set of Question object
	 * @return user object
	 */
	User addUserToQuestion(User usr,Set<Question> questions);     // in use
	
	/**
	 * Add User Entry to Event field
	 * @param usr user object 
	 * @param events Set of Event object
	 * @return user object
	 */
	User addUserToEvent(User usr,Set<Event> events);     // in use
	
	/**
	 * Add User Entry to Consultant field
	 * @param usr user object 
	 * @param consultant Set of Consultant object
	 * @return user object
	 */
	User addUserToConsultant(User usr,Set<Consultant> consultant);     // in use
	
	/**
	 * Add User Entry to Testimonial field
	 * @param usr user object 
	 * @param testi Set of Testimonial object
	 * @return user object
	 */
	User addUserToTestimonial(User usr,Set<Testimonial> testi);     // in use
	
	/**
	 * Add User Entry to ContributorAssignedTutorial field
	 * @param usr user object 
	 * @param testi Set of ContributorAssignedTutorial object
	 * @return user object
	 */
	User addUserToContributorTutorial(User usr,Set<ContributorAssignedTutorial> testi);     // in use
	
	/**
	 * Add User Entry to UserIndianLanguageMapping field
	 * @param usr user object 
	 * @param userMapping Set of UserIndianLanguageMapping object
	 * @return user object
	 */
	User addUserToUserIndianMapping(User usr,Set<UserIndianLanguageMapping> userMapping);  
	
	/**
	 * Find all the User object from database
	 * @return list of User object
	 */
	List<User> findAll();

	
}
