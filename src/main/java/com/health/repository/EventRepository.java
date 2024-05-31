package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Event;
import com.health.model.User;

public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query("select max(eventId) from Event")
    int getNewId();

    @Query("from Event e where e.user=?1")
    List<Event> findByuser(User usr);

    @CacheEvict(cacheNames = "events", allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(cacheNames = "events", allEntries = true)
    <S extends Event> S save(S entity);

    @Query("from Event e order by e.startDate desc")
    List<Event> getAllEvent();

    @Override
    List<Event> findAll();

}
