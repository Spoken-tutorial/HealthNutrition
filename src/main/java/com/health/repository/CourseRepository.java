
package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findByCourseId(int courseId);

    Course findByCourseName(String courseName);

    List<Course> findAllByStatus(boolean value);

}
