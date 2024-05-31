package com.health.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

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
import com.health.repository.RoleRepository;
import com.health.repository.UserRepository;
import com.health.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
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

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public long getNewId() {

        return userRepository.getNewId() + 1;

    }

    @Override
    public User addUserToCategory(User usr, Set<Category> categories) {

        usr.getCategories().addAll(categories);
        userRepository.save(usr);
        return null;
    }

    @Override
    public User addUserToTopic(User usr, Set<Topic> topics) {

        usr.getTopics().addAll(topics);
        userRepository.save(usr);
        return null;
    }

    @Override
    public User addUserToLanguage(User usr, Set<Language> languages) {

        usr.getLanguages().addAll(languages);
        userRepository.save(usr);
        return null;
    }

    @Override
    public User addUserToQuestion(User usr, Set<Question> questions) {

        usr.getQuestions().addAll(questions);
        userRepository.save(usr);
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "events", allEntries = true)
    public User addUserToEvent(User usr, Set<Event> events) {

        usr.getEvents().addAll(events);
        userRepository.save(usr);
        return null;
    }

    @Override
    public User addUserToConsultant(User usr, Set<Consultant> consultant) {

        usr.getConsults().addAll(consultant);
        userRepository.save(usr);
        return null;
    }

    @Override
    public User addUserToTestimonial(User usr, Set<Testimonial> testi) {

        usr.getTesti().addAll(testi);
        userRepository.save(usr);
        return null;
    }

    @Override
    public User addUserToUserIndianMapping(User usr, Set<UserIndianLanguageMapping> userMapping) {

        usr.getUserKnownLans().addAll(userMapping);
        userRepository.save(usr);
        return null;
    }

    @Override
    public User findBytoken(String token) {

        return userRepository.findBytoken(token);
    }

    @Override
    public List<User> findAll() {

        List<User> users = (List<User>) userRepository.findAll();

        return users;
    }

}
