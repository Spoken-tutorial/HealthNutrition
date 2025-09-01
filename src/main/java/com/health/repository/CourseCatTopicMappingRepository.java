package com.health.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT DISTINCT c.cat.categoryId FROM CourseCatTopicMapping c "
            + "WHERE c.course.courseId = :courseId AND c.status = true")
    List<Integer> findDistinctCatIdsByCourseIdAndStatusTrue(int courseId);

    @Query("SELECT DISTINCT c.cat.categoryId FROM CourseCatTopicMapping c " + "WHERE c.course.courseId = :courseId "
            + "AND c.status = true " + "AND c.cat.status = true")
    List<Integer> findDistinctEnabledCatIdsByCourseIdAndStatusTrue(int courseId);

    @Query("SELECT DISTINCT c.topic.topicId FROM CourseCatTopicMapping c "
            + "WHERE c.course.courseId = :courseId AND c.status = true")
    List<Integer> findDistinctTopicIdsByCourseIdAndStatusTrue(int courseId);

    List<CourseCatTopicMapping> findAllByCourse_CourseIdAndStatusTrue(int courseId);

    List<CourseCatTopicMapping> findAllByStatusTrue();
}
