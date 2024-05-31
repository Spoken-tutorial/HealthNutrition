package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Brouchure;
import com.health.model.Category;
import com.health.model.TopicCategoryMapping;
import com.health.repository.BrouchureRepository;
import com.health.repository.TopicCategoryMappingRepository;
import com.health.service.BrouchureService;

@Service
public class BrouchureServiceImpl implements BrouchureService {

    private static final Logger logger = LoggerFactory.getLogger(BrouchureServiceImpl.class);

    @Autowired
    private BrouchureRepository repo;

    @Autowired
    private TopicCategoryMappingRepository topicCatRepo;

    @Override
    public int getNewId() {

        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in brochure Service Impl class: {}", repo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(Brouchure temp) {

        repo.save(temp);
    }

    @Override

    public List<Brouchure> findAll() {

        return repo.findAll();
    }

    @Override
    public void delete(Brouchure temp) {

        repo.delete(temp);
    }

    @Override
    public List<Brouchure> findByOnHome(boolean value) {

        return repo.findAllByshowOnHomepage(value);
    }

    @Override
    public Brouchure findById(int id) {

        try {
            return repo.findById(id).get();
        } catch (Exception e) {

            logger.error("find By Id error in brochure Service Impl class: {}", id, e);
            return null;
        }
    }

    @Override
    public List<Brouchure> findByCategory(Category cat) {

        List<TopicCategoryMapping> topicCat = topicCatRepo.findAllBycat(cat);
        List<Brouchure> brochures = new ArrayList<Brouchure>();
        for (TopicCategoryMapping t : topicCat) {
            brochures.addAll(repo.findByTopicCat(t));
        }

        return brochures;
    }

    @Override
    @Cacheable(cacheNames = "brouchures")
    public List<Brouchure> findAllBrouchuresForCache() {

        return repo.findAllByshowOnHomepage(true);

    }

}
