package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Testimonial;
import com.health.repository.TestimonialRepository;
import com.health.service.TestimonialService;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    private static final Logger logger = LoggerFactory.getLogger(TestimonialServiceImpl.class);

    @Autowired
    private TestimonialRepository testRepo;

    @Override
    public List<Testimonial> findAll() {

        List<Testimonial> test = testRepo.findAll();
        return test;
    }

    @Override
    public void deleteProduct(Integer id) {

    }

    @Override
    public Testimonial findById(int id) {

        try {
            Optional<Testimonial> local = testRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error("Id error in Testimonial Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public int getNewTestimonialId() {

        try {
            return testRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Testimonial Service Impl: {}", testRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(Testimonial temp) {

        testRepo.save(temp);
    }

    @Override
    public List<Testimonial> findByApproved(boolean value) {

        return testRepo.findByapproved(value);
    }

    @Override
    @Cacheable(cacheNames = "testimonials")
    public List<Testimonial> findAllTestimonialByapprovedForCache() {

        return testRepo.findByapproved(true);
    }

}
