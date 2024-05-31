package com.health.service;

import java.util.List;

import com.health.model.Carousel;

public interface CarouselService {

    Carousel findById(int id);

    int getNewId();

    void save(Carousel temp);

    List<Carousel> findAll();

    List<Carousel> findByOnHome(boolean value);

    void delete(Carousel temp);

    List<Carousel> findCarouselForCache();
}
