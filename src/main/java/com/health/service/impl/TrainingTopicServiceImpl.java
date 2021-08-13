package com.health.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.TopicCategoryMapping;
import com.health.model.TrainingTopic;
import com.health.repository.TrainingTopicRepository;
import com.health.service.TrainingTopicService;

/**
 * Default implementation of the {@link com.health.service.TrainingTopicService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class TrainingTopicServiceImpl implements TrainingTopicService {

	@Autowired
	private TrainingTopicRepository trainingTopicRepo;
	
	/**
	 * @see com.health.service.TrainingTopicService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return trainingTopicRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}
	
	/**
	 * @see com.health.service.TrainingTopicService#findByTopicCat(List)
	 */
	@Override
	public Set<TrainingTopic> findByTopicCat(List<TopicCategoryMapping> topics) {
		// TODO Auto-generated method stub
		return trainingTopicRepo.findByTopicCat(topics);
	}

}
