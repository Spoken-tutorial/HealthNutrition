package com.health.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

/**
 * Default implementation of the
 * {@link com.health.service.TopicCategoryMappingService} interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class TopicCategoryMappingServiceImpl implements TopicCategoryMappingService {

    private static final Logger logger = LoggerFactory.getLogger(TopicCategoryMappingServiceImpl.class);

    @Autowired
    private TopicCategoryMappingRepository topicCatRepo;

    /**
     * @see com.health.service.TopicCategoryMappingService#save(TopicCategoryMapping)
     */
    @Override
    public void save(TopicCategoryMapping local) {
        // TODO Auto-generated method stub
        topicCatRepo.save(local);

    }

    /**
     * @see com.health.service.TopicCategoryMappingService#getNewId()
     */
    @Override
    public int getNewId() {
        // TODO Auto-generated method stub
        try {
            return topicCatRepo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(" New Id error in TopicCategory Mapping Service Impl: {}", topicCatRepo.getNewId(), e);
            return 1;
        }
    }

    /**
     * @see com.health.service.TopicCategoryMappingService#findAllByCategoryBasedOnUserRoles(List)
     */
    @Override
    public List<TopicCategoryMapping> findAllByCategoryBasedOnUserRoles(List<UserRole> userRoles) {

        // Use a Set to automatically handle uniqueness
        Set<TopicCategoryMapping> uniqueMappings = new HashSet<>();

        for (UserRole x : userRoles) {
            uniqueMappings.addAll(topicCatRepo.findAllBycat(x.getCategory()));
        }

        // Convert the Set back to a List
        return new ArrayList<>(uniqueMappings);
    }

    /**
     * @see com.health.service.TopicCategoryMappingService#findAllByCategoryAndTopic(Category,
     *      Topic)
     */
    @Override
    public TopicCategoryMapping findAllByCategoryAndTopic(Category cat, Topic topic) {
        // TODO Auto-generated method stub
        return topicCatRepo.findBycatAndtopic(cat, topic);
    }

    /**
     * @see com.health.service.TopicCategoryMappingService#findAllByCategory(Category)
     */
    @Override
    public List<TopicCategoryMapping> findAllByCategory(Category cat) {
        // TODO Auto-generated method stub
        return topicCatRepo.findAllBycat(cat);
    }

    /**
     * @see com.health.service.TopicCategoryMappingService#findAllByTopic(Topic)
     */
    @Override
    public List<TopicCategoryMapping> findAllByTopic(Topic topic) {
        // TODO Auto-generated method stub
        return topicCatRepo.findAllBytopic(topic);
    }

    @Override
    public List<TopicCategoryMapping> findAllByTopicwithCategoryTrue(Topic topic) {
        // TODO Auto-generated method stub
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

    /**
     * @see com.health.service.TopicCategoryMappingService#findByCategoryAndOrder(Category,
     *      int)
     */
    @Override
    public TopicCategoryMapping findByCategoryAndOrder(Category cat, int order) {
        // TODO Auto-generated method stub
        return topicCatRepo.findBycatAndorder(cat, order);
    }

    @Override
    public TopicCategoryMapping findByCategoryAndStatus(Category cat, Boolean status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TopicCategoryMapping> findAll() {

        return (List<TopicCategoryMapping>) topicCatRepo.findAll();
    }

}
