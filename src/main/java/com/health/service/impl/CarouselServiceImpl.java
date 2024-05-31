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

@Service
public class CarouselServiceImpl implements CarouselService {
    private static final Logger logger = LoggerFactory.getLogger(CarouselServiceImpl.class);

    @Autowired
    private CarouselRepository repo;

    @Override
    public int getNewId() {

        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Carousel Service Impl class: {}", repo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(Carousel temp) {

        repo.save(temp);
    }

    @Override
    public List<Carousel> findAll() {

        return repo.findAll();
    }

    @Override
    public List<Carousel> findByOnHome(boolean value) {

        return repo.findAllByshowOnHomepage(value);
    }

    @Override
    public void delete(Carousel temp) {

        repo.delete(temp);
    }

    @Override
    public Carousel findById(int id) {
        try {
            Optional<Carousel> local = repo.findById(id);
            return local.get();
        } catch (Exception e) {

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
