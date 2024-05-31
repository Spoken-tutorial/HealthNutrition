package com.health.service;

import java.util.List;

import com.health.model.Language;
import com.health.model.Question;
import com.health.model.TopicCategoryMapping;

public interface QuestionService {

    int getNewId();

    void save(Question ques);

    Question findById(int id);

    Question getQuestionBasedOnTopicCatAndLan(TopicCategoryMapping topicCat, Language lan);

    List<Question> findAll();
}
