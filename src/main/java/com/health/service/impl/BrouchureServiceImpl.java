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

/**
 * Default implementation of the {@link com.health.service.BrouchureService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class BrouchureServiceImpl implements BrouchureService {

    private static final Logger logger = LoggerFactory.getLogger(BrouchureServiceImpl.class);

    @Autowired
    private BrouchureRepository repo;

    @Autowired
    private TopicCategoryMappingRepository topicCatRepo;

    /**
     * @see com.health.service.BrouchureService#getNewId()
     */
    @Override
    public int getNewId() {
        // TODO Auto-generated method stub
        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("New Id error in brochure Service Impl class: {}", repo.getNewId(), e);
            return 1;
        }
    }

    /**
     * @see com.health.service.BrouchureService#save(Brouchure)
     */
    @Override
    public void save(Brouchure temp) {
        // TODO Auto-generated method stub
        repo.save(temp);
    }

    /**
     * @see com.health.service.BrouchureService#findAll()
     */
    @Override
    // @Cacheable(cacheNames ="brouchures" )
    public List<Brouchure> findAll() {
        // TODO Auto-generated method stub
        // return (List<Brouchure>) repo.findAll();
        return repo.findAll();
    }

    /**
     * @see com.health.service.BrouchureService#delete(Brouchure)
     */
    @Override
    public void delete(Brouchure temp) {
        // TODO Auto-generated method stub
        repo.delete(temp);
    }

    /**
     * @see com.health.service.BrouchureService#findByOnHome(boolean)
     */
    @Override
    public List<Brouchure> findByOnHome(boolean value) {
        // TODO Auto-generated method stub
        return repo.findAllByshowOnHomepage(value);
    }

    /**
     * @see com.health.service.BrouchureService#findById(int)
     */
    @Override
    public Brouchure findById(int id) {
        // TODO Auto-generated method stub
        try {
            return repo.findById(id).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("find By Id error in brochure Service Impl class: {}", id, e);
            return null;
        }
    }

    /**
     * @see com.health.service.impl.BrouchureServiceImpl#findByCategory(Category)
     */
    @Override
    public List<Brouchure> findByCategory(Category cat) {
        // TODO Auto-generated method stub
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
