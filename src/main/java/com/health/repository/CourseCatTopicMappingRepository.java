package com.health.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.Category;
import com.health.model.Course;
import com.health.model.CourseCatTopicMapping;
import com.health.model.Topic;

public interface CourseCatTopicMappingRepository extends JpaRepository<CourseCatTopicMapping, Integer> {

    CourseCatTopicMapping findByCourseCatTopicId(int courseCatTopicId);

    List<CourseCatTopicMapping> findByCat(Category cat);

    List<CourseCatTopicMapping> findByTopic(Topic topic);

    List<CourseCatTopicMapping> findByCourse(Course course);

    List<CourseCatTopicMapping> findByCatAndTopic(Category cat, Topic topic);

    Optional<CourseCatTopicMapping> findByCourseAndCatAndTopic(Course course, Category cat, Topic topic);
}
