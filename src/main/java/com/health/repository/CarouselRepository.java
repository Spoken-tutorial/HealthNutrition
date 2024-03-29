package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Carousel;

/**
 * This Interface Extend CrudRepository to handle all database operation related
 * to Carousel object
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface CarouselRepository extends CrudRepository<Carousel, Integer> {

    /**
     * Find the next unique id for the object
     * 
     * @return primitive integer value
     */
    @Query("select max(id) from Carousel")
    Integer getNewId();

    @CacheEvict(cacheNames = "carousels", allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(cacheNames = "carousels", allEntries = true)
    <S extends Carousel> S save(S entity);

    /**
     * Find all the Carousel based on given showOnHomePage field
     * 
     * @param value showOnHomePage field
     * @return List of Carousel object
     */
    List<Carousel> findAllByshowOnHomepage(boolean value);

    // @Cacheable(cacheNames ="carousels")
    @Override
    List<Carousel> findAll();
}
