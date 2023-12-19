package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.PromoVideo;
import com.health.repository.promoVideoRepository;
import com.health.service.PromoVideoService;

@Service
public class promoVideoServiceImpl implements PromoVideoService {

    private static final Logger logger = LoggerFactory.getLogger(promoVideoServiceImpl.class);

    @Autowired
    promoVideoRepository promoVideoRepository;

    @Override
    public int getNewId() {
        try {
            return promoVideoRepository.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" New Id error in Promo Video Service Impl: {}", promoVideoRepository.getNewId(), e);
            return 1;
        }
    }

    @Override
    public void save(PromoVideo promoVideo) {

        promoVideoRepository.save(promoVideo);

    }

    @Override
    public List<PromoVideo> findAll() {

        return promoVideoRepository.findAll();

    }

    @Override
    public List<PromoVideo> findAllByShowOnHomePage() {

        return promoVideoRepository.findAllByshowOnHomepage(true);

    }

    @Override
    public void delete(PromoVideo temp) {

        promoVideoRepository.delete(temp);
    }

    @Override
    public PromoVideo findById(int id) {
        try {
            return promoVideoRepository.findById(id).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" Id error in Promo Video Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public void saveAll(List<PromoVideo> promovideos) {
        promoVideoRepository.saveAll(promovideos);
    }

}
