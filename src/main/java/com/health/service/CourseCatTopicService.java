package com.health.service;

import java.util.List;

import com.health.model.Category;
import com.health.model.Course;
import com.health.model.CourseCatTopicMapping;
import com.health.model.Topic;

public interface CourseCatTopicService {
    CourseCatTopicMapping findByCourseCatTopicId(int courseCatTopicId);

    List<CourseCatTopicMapping> findByCat(Category cat);

    List<CourseCatTopicMapping> findByTopic(Topic topic);

    List<CourseCatTopicMapping> findByCourse(Course course);

    List<CourseCatTopicMapping> findByCatAndTopic(Category cat, Topic topic);

    List<CourseCatTopicMapping> findAll();

    void save(CourseCatTopicMapping courseCatTopicMapping);

    void saveAll(List<CourseCatTopicMapping> courseCatTopicMappings);
}
