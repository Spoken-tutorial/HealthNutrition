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

    void delete(CourseCatTopicMapping courseCatTopicMapping);

    CourseCatTopicMapping findByCourseAndCatAndTopic(Course course, Category cat, Topic topic);

    List<Integer> findDistinctCatIdsByCourseIdAndStatusTrue(int courseId);

    List<Integer> findDistinctEnabledCatIdsByCourseIdAndStatusTrue(int courseId);

    List<Integer> findDistinctTopicIdsByCourseIdAndStatusTrue(int courseId);

    List<CourseCatTopicMapping> findAllByCourse_CourseIdAndStatusTrue(int courseId);

    List<CourseCatTopicMapping> findAllByStatusTrue();

}
