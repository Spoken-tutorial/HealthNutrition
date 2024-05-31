package com.health.service;

import java.util.List;
import java.util.Set;

import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Consultant;
import com.health.model.Event;
import com.health.model.Language;
import com.health.model.Question;
import com.health.model.Testimonial;
import com.health.model.Topic;
import com.health.model.User;
import com.health.model.UserIndianLanguageMapping;

public interface UserService {

    User findBytoken(String token);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);

    long getNewId();

    User addUserToCategory(User usr, Set<Category> categories);

    User addUserToTopic(User usr, Set<Topic> topics);

    User addUserToLanguage(User usr, Set<Language> languages);

    User addUserToQuestion(User usr, Set<Question> questions);

    User addUserToEvent(User usr, Set<Event> events);

    User addUserToConsultant(User usr, Set<Consultant> consultant);

    User addUserToTestimonial(User usr, Set<Testimonial> testi);

    User addUserToUserIndianMapping(User usr, Set<UserIndianLanguageMapping> userMapping);

    List<User> findAll();

}
