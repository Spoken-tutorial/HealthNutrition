package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Course;
import com.health.repository.CourseRepository;
import com.health.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repo;

    @Override
    public Course findByCourseId(int courseId) {

        return repo.findByCourseId(courseId);
    }

    @Override
    public Course findByCourseName(String courseName) {
        return repo.findByCourseName(courseName);
    }

    @Override
    public List<Course> findAllByStatus(boolean value) {

        return repo.findAllByStatus(value);
    }

    @Override
    public List<Course> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(Course course) {

        repo.save(course);
    }

    @Override
    public void saveAll(List<Course> courses) {

        repo.saveAll(courses);
    }

}
