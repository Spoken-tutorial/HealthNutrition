package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.Event;
import com.health.model.User;
import com.health.repository.EventRepository;
import com.health.repository.RoleRepository;
import com.health.service.EventService;
import com.health.utility.CommonData;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public List<Event> findAll() {

        List<Event> local = eventRepo.getAllEvent();
        return local;

    }

    @Override
    public void deleteProduct(Integer id) {

        eventRepo.deleteById(id);

    }

    @Override
    public Event findById(int id) {

        try {
            Optional<Event> local = eventRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error(" Id error in Event Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public int getNewEventId() {

        try {
            return eventRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Event Service Impl: {}", eventRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(Event event) {

        eventRepo.save(event);
    }

    @Override
    public List<Event> findByUser(User usr) {
        Role adminRole = roleRepo.findByname(CommonData.superUserRole);
        for (UserRole item : usr.getUserRoles()) {
            if (item.getRole() == adminRole) {
                return eventRepo.findAll();
            }
        }
        return eventRepo.findByuser(usr);
    }

    @Override
    @Cacheable(cacheNames = "events")
    public List<Event> findAllEventForCache() {
        return eventRepo.findAll();
    }

}
