package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Comment;
import com.health.model.Tutorial;
import com.health.model.User;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    @Query("select max(commentId) from Comment")
    int getNewId();

    @Query("from Comment where type=?1 and user=?2 and tutorialInfos=?3 and roleName=?4")
    List<Comment> getCommentBasedOnUserTutorialType(String type, User usr, Tutorial tut, String roleName);

    @Query("from Comment where type=?1 and tutorialInfos=?2")
    List<Comment> getCommentBasedOnTutorialType(String type, Tutorial tut);
}
