package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.User;

public interface ContributorAssignedMultiUserTutorialRepository
        extends CrudRepository<ContributorAssignedMultiUserTutorial, Integer> {

    @Query("select max(id) from ContributorAssignedMultiUserTutorial")
    int getNewId();

    List<ContributorAssignedMultiUserTutorial> findAllByuser(User user);

    @Override
    List<ContributorAssignedMultiUserTutorial> findAll();

}
