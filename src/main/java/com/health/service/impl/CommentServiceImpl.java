package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Comment;
import com.health.model.Tutorial;
import com.health.model.User;
import com.health.repository.CommentRepository;
import com.health.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentRepository comRepo;

    @Override
    public int getNewCommendId() {

        try {
            return comRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Comment Service Impl class: {}", comRepo.getNewId(), e);

            return 1;
        }
    }

    @Override
    public void save(Comment com) {

        comRepo.save(com);
    }

    @Override
    public List<Comment> getCommentBasedOnUserTutorialType(String type, User usr, Tutorial tut, String role) {

        return comRepo.getCommentBasedOnUserTutorialType(type, usr, tut, role);
    }

    @Override
    public List<Comment> getCommentBasedOnTutorialType(String type, Tutorial tut) {

        return comRepo.getCommentBasedOnTutorialType(type, tut);
    }
}
