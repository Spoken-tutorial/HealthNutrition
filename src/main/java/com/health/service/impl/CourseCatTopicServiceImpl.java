package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Category;
import com.health.model.Course;
import com.health.model.CourseCatTopicMapping;
import com.health.model.Topic;
import com.health.repository.CourseCatTopicMappingRepository;
import com.health.service.CourseCatTopicService;

@Service
public class CourseCatTopicServiceImpl implements CourseCatTopicService {

    @Autowired
    private CourseCatTopicMappingRepository repo;

    @Override
    public CourseCatTopicMapping findByCourseCatTopicId(int courseCatTopicId) {

        return repo.findByCourseCatTopicId(courseCatTopicId);
    }

    @Override
    public List<CourseCatTopicMapping> findByCat(Category cat) {

        return repo.findByCat(cat);
    }

    @Override
    public List<CourseCatTopicMapping> findByTopic(Topic topic) {

        return repo.findByTopic(topic);
    }

    @Override
    public List<CourseCatTopicMapping> findByCourse(Course course) {

        return repo.findByCourse(course);
    }

    @Override
    public List<CourseCatTopicMapping> findByCatAndTopic(Category cat, Topic topic) {

        return repo.findByCatAndTopic(cat, topic);
    }

    @Override
    public List<CourseCatTopicMapping> findAll() {

        return repo.findAll();
    }

    @Override
    public void save(CourseCatTopicMapping courseCatTopicMapping) {
        repo.save(courseCatTopicMapping);

    }

    @Override
    public void saveAll(List<CourseCatTopicMapping> courseCatTopicMappings) {
        repo.saveAll(courseCatTopicMappings);

    }

    @Override
    public void delete(CourseCatTopicMapping courseCatTopicMapping) {
        repo.delete(courseCatTopicMapping);

    }

}
