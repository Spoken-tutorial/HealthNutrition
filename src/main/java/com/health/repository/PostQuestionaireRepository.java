package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.PostQuestionaire;
import com.health.model.User;

public interface PostQuestionaireRepository extends CrudRepository<PostQuestionaire, Integer> {

    @Query("select max(id) from PostQuestionaire")
    int getNewId();

    @Query("from PostQuestionaire where user=?1")
    List<PostQuestionaire> findByUser(User usr);

}
