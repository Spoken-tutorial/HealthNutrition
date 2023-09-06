package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Language;
import com.health.model.PathofPromoVideo;
import com.health.model.PromoVideo;
import com.health.repository.PathofPromoVideoRepository;
import com.health.service.PathofPromoVideoService;

@Service
public class PathofPromoVideoServiceImpl implements PathofPromoVideoService {

    private static final Logger logger = LoggerFactory.getLogger(PathofPromoVideoServiceImpl.class);

    @Autowired
    private PathofPromoVideoRepository pathofPromoVideoRepository;

    @Override
    public int getNewId() {
        try {
            return pathofPromoVideoRepository.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" New Id error in  Path of Promo Video Service Impl: {}",
                    pathofPromoVideoRepository.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(PathofPromoVideo pathofPromoVideo) {
        pathofPromoVideoRepository.save(pathofPromoVideo);

    }

    @Override
    public List<PathofPromoVideo> findAll() {

        return pathofPromoVideoRepository.findAll();
    }

    @Override
    public void delete(PathofPromoVideo temp) {
        pathofPromoVideoRepository.delete(temp);

    }

    @Override
    public PathofPromoVideo findById(int id) {
        try {
            return pathofPromoVideoRepository.findById(id).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("  Id error in  Path of Promo Video Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public List<PathofPromoVideo> findByPromoVideo(PromoVideo promoVideo) {

        return pathofPromoVideoRepository.findByPromoVideo(promoVideo);
    }

    @Override
    public List<PathofPromoVideo> findByLanguage(Language lan) {

        return pathofPromoVideoRepository.findByLanguage(lan);
    }

    @Override
    public PathofPromoVideo findByLanguageandPromoVideo(Language lan, PromoVideo promoVideo) {

        return pathofPromoVideoRepository.findByLanguageAndPromoVideo(lan, promoVideo);
    }

    @Override
    public List<String> findAlllangNames(PromoVideo promoVideo) {

        List<PathofPromoVideo> listofpathofVideos = pathofPromoVideoRepository.findByPromoVideo(promoVideo);
        List<String> langNames = new ArrayList<>();

        for (PathofPromoVideo temp : listofpathofVideos) {

            langNames.add(temp.getLan().getLangName());
        }
        return langNames;
    }

    @Override
    public String GetPromoVideofFirstLan(PromoVideo promoVideo) {
        List<PathofPromoVideo> listofpathofVideos = pathofPromoVideoRepository.findByPromoVideo(promoVideo);
        PathofPromoVideo pathofPromoVideo = listofpathofVideos.get(0);
        return pathofPromoVideo.getVideoPath();
    }

    @Override
    public void saveAll(List<PathofPromoVideo> pathofPromoVideos) {
        pathofPromoVideoRepository.saveAll(pathofPromoVideos);

    }

}
