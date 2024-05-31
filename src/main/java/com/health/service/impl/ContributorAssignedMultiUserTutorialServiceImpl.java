package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.User;
import com.health.repository.ContributorAssignedMultiUserTutorialRepository;
import com.health.service.ContributorAssignedMultiUserTutorialService;

@Service
public class ContributorAssignedMultiUserTutorialServiceImpl implements ContributorAssignedMultiUserTutorialService {
    private static final Logger logger = LoggerFactory.getLogger(ContributorAssignedMultiUserTutorialServiceImpl.class);

    @Autowired
    private ContributorAssignedMultiUserTutorialRepository conRepo;

    @Override
    public void save(ContributorAssignedMultiUserTutorial con) {

        conRepo.save(con);

    }

    @Override
    public int getNewId() {

        try {
            return conRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in ContributorAssigned MultiUser TutorialService Impl: {}", conRepo.getNewId(),
                    e);
            return 1;
        }
    }

    @Override
    public List<ContributorAssignedMultiUserTutorial> getAllByuser(User usr) {

        return conRepo.findAllByuser(usr);
    }

}
