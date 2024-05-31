package com.health.service;

import java.util.List;

import com.health.model.Event;
import com.health.model.User;

public interface EventService {

    List<Event> findAll();

    void deleteProduct(Integer id);

    Event findById(int id);

    int getNewEventId();

    void save(Event event);

    List<Event> findByUser(User usr);

    List<Event> findAllEventForCache();

}
