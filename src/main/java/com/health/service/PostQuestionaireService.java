package com.health.service;

import java.util.List;

import com.health.model.PostQuestionaire;
import com.health.model.User;

public interface PostQuestionaireService {

    int getNewCatId();

    void save(PostQuestionaire temp);

    List<PostQuestionaire> findAll();

    List<PostQuestionaire> findByUser(User user);
}
