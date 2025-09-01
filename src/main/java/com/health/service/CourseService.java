package com.health.service;

import java.util.List;

import com.health.model.Course;

public interface CourseService {

    Course findByCourseId(int courseId);

    Course findByCourseName(String courseName);

    List<Course> findAllByStatus(boolean value);

    List<Course> findAll();

    void save(Course course);

    void saveAll(List<Course> courses);
}
