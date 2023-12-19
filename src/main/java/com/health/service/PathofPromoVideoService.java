package com.health.service;

import java.util.List;

import com.health.model.Language;
import com.health.model.PathofPromoVideo;
import com.health.model.PromoVideo;

public interface PathofPromoVideoService {

    /**
     * Find the next unique id for brochure object
     * 
     * @return primitive integer value
     */
    int getNewId();

    void save(PathofPromoVideo pathofPromoVideo);

    List<PathofPromoVideo> findAll();

    void delete(PathofPromoVideo temp);

    PathofPromoVideo findById(int id);

    List<PathofPromoVideo> findByPromoVideo(PromoVideo promoVideo);

    List<PathofPromoVideo> findByLanguage(Language lan);

    PathofPromoVideo findByLanguageandPromoVideo(Language lan, PromoVideo promoVideo);

    List<String> findAlllangNames(PromoVideo promoVideo);

    String GetPromoVideofFirstLan(PromoVideo promoVideo);

    void saveAll(List<PathofPromoVideo> pathofPromoVideos);

}
