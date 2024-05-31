package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Testimonial;

public interface TestimonialRepository extends CrudRepository<Testimonial, Integer> {

    @Query("select max(testimonialId) from Testimonial")
    int getNewId();

    @CacheEvict(cacheNames = "testimonials", allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(cacheNames = "testimonials", allEntries = true)
    <S extends Testimonial> S save(S entity);

    List<Testimonial> findByapproved(boolean value);

    @Override
    List<Testimonial> findAll();

}
