package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Language;
import com.health.model.PathofPromoVideo;
import com.health.model.PromoVideo;

public interface PathofPromoVideoRepository extends CrudRepository<PathofPromoVideo, Integer> {

    @Query("select max(pathPromoId) from PathofPromoVideo")
    int getNewId();

    PathofPromoVideo findByLan(Language lan);

    void deleteBypathPromoId(int pathPromoId);

    @Query("from PathofPromoVideo where promoVideo = ?1")
    List<PathofPromoVideo> findByPromoVideo(PromoVideo promoVideo);

    @Query("from PathofPromoVideo where lan = ?1")
    List<PathofPromoVideo> findByLanguage(Language lan);

    @Query("from PathofPromoVideo where lan = ?1 and promoVideo=?2")
    PathofPromoVideo findByLanguageAndPromoVideo(Language lan, PromoVideo promoVideo);

    @Override
    List<PathofPromoVideo> findAll();

}
