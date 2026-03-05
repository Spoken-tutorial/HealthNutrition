package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.TopicLanMapping;
import com.health.model.TrainingResource;

public interface TrainingResourceRepository extends JpaRepository<TrainingResource, Integer> {

    TrainingResource findByTrainingResourceId(int trainingResourceId);

    List<TrainingResource> findByTopicLanMapping(TopicLanMapping topicLanMapping);

    List<TrainingResource> findByTopicLanMappingInAndStatusTrue(List<TopicLanMapping> topicLanMappingList);

    List<TrainingResource> findAllByStatusTrue();

    TrainingResource findByPdfToken(String pdfToken);

    TrainingResource findByDocToken(String docToken);

    TrainingResource findByExcelToken(String excelToken);

    TrainingResource findByImgToken(String docToken);

    boolean existsByPdfTokenOrDocTokenOrExcelTokenOrImgToken(String pdfToken, String docToken, String excelToken,
            String imgToken);

}
