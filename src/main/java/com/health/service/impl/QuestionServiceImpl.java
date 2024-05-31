package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Language;
import com.health.model.Question;
import com.health.model.TopicCategoryMapping;
import com.health.repository.QuestionRepository;
import com.health.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionRepository questionRepo;

    @Override
    public int getNewId() {

        try {
            return questionRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in Question Service Impl: {}", questionRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(Question ques) {

        questionRepo.save(ques);
    }

    @Override
    public Question findById(int id) {

        try {
            Optional<Question> local = questionRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error(" Id error in Question Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public Question getQuestionBasedOnTopicCatAndLan(TopicCategoryMapping topicCat, Language lan) {

        return questionRepo.findByTopicLan(topicCat, lan);
    }

    @Override
    public List<Question> findAll() {

        return (List<Question>) questionRepo.findAll();
    }

}
