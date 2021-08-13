package com.health.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.health.repository.RoleRepository;
import com.health.repository.UserRepository;
import com.health.service.UserService;

/**
 * Default implementation of the {@link com.health.service.UserService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	/**
	 * @see com.health.service.UserService#findByUsername(String)
	 * 
	 */
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	/**
	 * @see com.health.service.UserService#findByEmail(String)
	 */
	@Override
	public User findByEmail (String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * @see com.health.service.UserService#createUser(User, Set)
	 */
	@Override
	public User createUser(User user, Set<UserRole> userRoles){
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
			
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			localUser = userRepository.save(user);
		}
		
		return localUser;
	}
	
	/**
	 * @see com.health.service.UserService#save(User)
	 */
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * @see com.health.service.UserService#getNewId()
	 */
	@Override
	public long getNewId() {
		// TODO Auto-generated method stub
		return userRepository.getNewId()+1;
		
	}

	/**
	 * @see com.health.service.UserService#addUserToCategory(User, Set)
	 */
	@Override
	public User addUserToCategory(User usr, Set<Category> categories) {
		// TODO Auto-generated method stub
		
		usr.getCategories().addAll(categories);
		userRepository.save(usr);
		return null;
	}

	/**
	 * @see com.health.service.UserService#addUserToTopic(User, Set)
	 */
	@Override
	public User addUserToTopic(User usr, Set<Topic> topics) {
		// TODO Auto-generated method stub
		usr.getTopics().addAll(topics);
		userRepository.save(usr);
		return null;
	}

	/**
	 * @see com.health.service.UserService#addUserToLanguage(User, Set)
	 */
	@Override
	public User addUserToLanguage(User usr, Set<Language> languages) {
		// TODO Auto-generated method stub
		
		usr.getLanguages().addAll(languages);
		userRepository.save(usr);
		return null;
	}

	/**
	 * @see com.health.service.UserService#addUserToQuestion(User, Set)
	 */
	@Override
	public User addUserToQuestion(User usr, Set<Question> questions) {
		// TODO Auto-generated method stub
		usr.getQuestions().addAll(questions);
		userRepository.save(usr);
		return null;
	}

	/**
	 * @see com.health.service.UserService#addUserToEvent(User, Set)
	 */
	@Override
	public User addUserToEvent(User usr, Set<Event> events) {
		// TODO Auto-generated method stub
		usr.getEvents().addAll(events);
		userRepository.save(usr);
		return null;
	}

	/**
	 * @see com.health.service.UserService#addUserToConsultant(User, Set)
	 */
	@Override
	public User addUserToConsultant(User usr, Set<Consultant> consultant) {
		// TODO Auto-generated method stub
		usr.getConsults().addAll(consultant);
		userRepository.save(usr);
		return null;
	}

	/**
	 * @see com.health.service.UserService#addUserToTestimonial(User, Set)
	 */
	@Override
	public User addUserToTestimonial(User usr, Set<Testimonial> testi) {
		// TODO Auto-generated method stub
		usr.getTesti().addAll(testi);
		userRepository.save(usr);
		return null;
	}

	/**
	 * @see com.health.service.UserService#addUserToContributorTutorial(User, Set)
	 */
	@Override
	public User addUserToContributorTutorial(User usr, Set<ContributorAssignedTutorial> testi) {
		// TODO Auto-generated method stub
//		usr.getConAssignedTutorial().addAll(testi);
//		userRepository.save(usr);
		return null;
		
	}

	/**
	 * @see com.health.service.UserService#addUserToUserIndianMapping(User, Set)
	 */
	@Override
	public User addUserToUserIndianMapping(User usr, Set<UserIndianLanguageMapping> userMapping) {
		// TODO Auto-generated method stub
		usr.getUserKnownLans().addAll(userMapping);
		userRepository.save(usr);
		return null;
	}

	/**
	 * @see com.health.service.UserService#findBytoken(String)
	 */
	@Override
	public User findBytoken(String token) {
		// TODO Auto-generated method stub
		return userRepository.findBytoken(token);
	}

	/**
	 * @see com.health.service.UserService#findAll()
	 */
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		List<User> users= (List<User>) userRepository.findAll();
		
		return users;
	}


	
}
