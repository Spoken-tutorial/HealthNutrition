package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Carousel;
import com.health.repository.CarouselRepository;
import com.health.service.CarouselService;

/**
 * Default implementation of the {@link com.health.service.CarouselService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class CarouselServiceImpl implements CarouselService {
    private static final Logger logger = LoggerFactory.getLogger(CarouselServiceImpl.class);

    @Autowired
    private CarouselRepository repo;

    /**
     * @see com.health.service.CarouselService#getNewId()
     */
    @Override
    public int getNewId() {
        // TODO Auto-generated method stub
        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("New Id error in Carousel Service Impl class: {}", repo.getNewId(), e);
            return 1;
        }
    }

    /**
     * @see com.health.service.CarouselService#save(Carousel)
     */
    @Override
    public void save(Carousel temp) {
        // TODO Auto-generated method stub
        repo.save(temp);
    }

    /**
     * @see com.health.service.CarouselService#findAll()
     */
    @Override
    public List<Carousel> findAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    /**
     * @see com.health.service.CarouselService#findByOnHome(boolean)
     */
    @Override
    public List<Carousel> findByOnHome(boolean value) {
        // TODO Auto-generated method stub
        return repo.findAllByshowOnHomepage(value);
    }

    /**
     * @see com.health.service.CarouselService#delete(Carousel)
     */
    @Override
    public void delete(Carousel temp) {
        // TODO Auto-generated method stub
        repo.delete(temp);
    }

    @Override
    public Carousel findById(int id) {
        try {
            Optional<Carousel> local = repo.findById(id);
            return local.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("find By Id error in Carousel Service Impl class: {}", id, e);
            return null;
        }
    }

    @Override
    @Cacheable(cacheNames = "carousels")
    public List<Carousel> findCarouselForCache() {
        return repo.findAll();
    }
}
