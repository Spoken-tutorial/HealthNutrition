package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.ResearchPaper;
import com.health.repository.ResearchPaperRepository;
import com.health.service.ResearchPaperService;

@Service
public class ResearchPaperServiceImpl implements ResearchPaperService {

    private static final Logger logger = LoggerFactory.getLogger(ResearchPaperServiceImpl.class);

    @Autowired
    private ResearchPaperRepository repo;

    @Override
    public int getNewId() {
        // TODO Auto-generated method stub
        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" New Id error in Research Paper Service Impl: {}", repo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(ResearchPaper temp) {
        // TODO Auto-generated method stub
        repo.save(temp);
    }

    @Override

    public List<ResearchPaper> findAll() {

        return (List<ResearchPaper>) repo.findAll();
    }

    @Override
    public void delete(ResearchPaper temp) {
        // TODO Auto-generated method stub
        repo.delete(temp);
    }

    @Override
    public List<ResearchPaper> findByOnHome(boolean value) {
        // TODO Auto-generated method stub
        return repo.findAllByshowOnHomepage(value);
    }

    @Override
    public List<ResearchPaper> findAllByShowOnHomePage() {

        return repo.findAllByshowOnHomepage(true);

    }

    @Override
    public ResearchPaper findById(int id) {
        // TODO Auto-generated method stub
        try {
            return repo.findById(id).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Id error in Research Paper Service Impl: {}", id, e);
            return null;
        }
    }

}
