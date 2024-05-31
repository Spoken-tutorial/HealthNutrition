package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;
import com.health.repository.TopicCategoryMappingRepository;
import com.health.service.TopicCategoryMappingService;

@Service
public class TopicCategoryMappingServiceImpl implements TopicCategoryMappingService {

    private static final Logger logger = LoggerFactory.getLogger(TopicCategoryMappingServiceImpl.class);

    @Autowired
    private TopicCategoryMappingRepository topicCatRepo;

    @Override
    public void save(TopicCategoryMapping local) {

        topicCatRepo.save(local);

    }

    @Override
    public int getNewId() {

        try {
            return topicCatRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in TopicCategory Mapping Service Impl: {}", topicCatRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public List<TopicCategoryMapping> findAllByCategoryBasedOnUserRoles(List<UserRole> userRoles) {

        List<TopicCategoryMapping> temp = new ArrayList<TopicCategoryMapping>();
        for (UserRole x : userRoles) {

            temp.addAll(topicCatRepo.findAllBycat(x.getCategory()));
        }

        return temp;
    }

    @Override
    public TopicCategoryMapping findAllByCategoryAndTopic(Category cat, Topic topic) {

        return topicCatRepo.findBycatAndtopic(cat, topic);
    }

    @Override
    public List<TopicCategoryMapping> findAllByCategory(Category cat) {

        return topicCatRepo.findAllBycat(cat);
    }

    @Override
    public List<TopicCategoryMapping> findAllByTopic(Topic topic) {

        return topicCatRepo.findAllBytopic(topic);
    }

    @Override
    public List<TopicCategoryMapping> findAllByTopicwithCategoryTrue(Topic topic) {

        List<TopicCategoryMapping> list1 = new ArrayList<>();
        List<TopicCategoryMapping> list2 = topicCatRepo.findAllBytopic(topic);
        for (TopicCategoryMapping temp : list2) {
            if (temp.getCat().isStatus()) {
                list1.add(temp);
                ;
            }
        }
        return list1;
    }

    @Override
    public TopicCategoryMapping findByCategoryAndOrder(Category cat, int order) {

        return topicCatRepo.findBycatAndorder(cat, order);
    }

    @Override
    public TopicCategoryMapping findByCategoryAndStatus(Category cat, Boolean status) {

        return null;
    }

    @Override
    public List<TopicCategoryMapping> findAll() {

        return (List<TopicCategoryMapping>) topicCatRepo.findAll();
    }

}
