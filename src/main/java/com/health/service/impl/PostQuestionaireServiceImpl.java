package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.PostQuestionaire;
import com.health.model.User;
import com.health.repository.PostQuestionaireRepository;
import com.health.service.PostQuestionaireService;

@Service
public class PostQuestionaireServiceImpl implements PostQuestionaireService {

    private static final Logger logger = LoggerFactory.getLogger(PostQuestionaireServiceImpl.class);

    @Autowired
    private PostQuestionaireRepository repo;

    @Override
    public int getNewCatId() {

        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in Post QuestionService Impl: {}", repo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(PostQuestionaire temp) {

        repo.save(temp);
    }

    @Override
    public List<PostQuestionaire> findAll() {

        return (List<PostQuestionaire>) repo.findAll();
    }

    @Override
    public List<PostQuestionaire> findByUser(User user) {

        return repo.findByUser(user);
    }
}
