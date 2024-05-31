package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Carousel;

public interface CarouselRepository extends CrudRepository<Carousel, Integer> {

    @Query("select max(id) from Carousel")
    Integer getNewId();

    @CacheEvict(cacheNames = "carousels", allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(cacheNames = "carousels", allEntries = true)
    <S extends Carousel> S save(S entity);

    List<Carousel> findAllByshowOnHomepage(boolean value);

    @Override
    List<Carousel> findAll();
}
