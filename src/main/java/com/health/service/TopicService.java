package com.health.service;

import java.util.List;

import com.health.model.Topic;

public interface TopicService {

    int getNewTopicId();

    Topic findBytopicName(String topic);

    Topic findById(int id);

    List<Topic> findAll();

    void save(Topic topic);

    List<Topic> getTopicsForcache();
}
