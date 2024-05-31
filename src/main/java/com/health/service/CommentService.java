package com.health.service;

import java.util.List;

import com.health.model.Comment;
import com.health.model.Tutorial;
import com.health.model.User;

public interface CommentService {

    int getNewCommendId();

    void save(Comment com);

    List<Comment> getCommentBasedOnUserTutorialType(String type, User usr, Tutorial tut, String role);

    List<Comment> getCommentBasedOnTutorialType(String type, Tutorial tut);
}
