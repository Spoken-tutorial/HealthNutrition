package com.health.service;

import java.util.List;

import com.health.model.Testimonial;

public interface TestimonialService {

    List<Testimonial> findAll();

    void deleteProduct(Integer id);

    Testimonial findById(int id);

    int getNewTestimonialId();

    void save(Testimonial temp);

    List<Testimonial> findByApproved(boolean value);

    List<Testimonial> findAllTestimonialByapprovedForCache();

}
