package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.PromoVideo;

public interface promoVideoRepository extends CrudRepository<PromoVideo, Integer> {

    @Query("select max(promoId) from PromoVideo")
    int getNewId();

    @Override
    List<PromoVideo> findAll();

    List<PromoVideo> findAllByshowOnHomepage(boolean value);

}
