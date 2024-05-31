package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.FeedbackMasterTrainer;
import com.health.model.User;

public interface FeedBackRepository extends CrudRepository<FeedbackMasterTrainer, Integer> {

    @Query("select max(TrainerFeedId) from FeedbackMasterTrainer")
    int getNewId();

    @Query("from FeedbackMasterTrainer where user=?1")
    List<FeedbackMasterTrainer> findByUser(User usr);

}
