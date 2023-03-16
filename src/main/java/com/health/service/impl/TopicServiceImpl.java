package com.health.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Category;
import com.health.model.Topic;
import com.health.model.Tutorial;
import com.health.repository.TopicRepository;
import com.health.repository.TutorialRepository;
import com.health.service.TopicService;

/**
 * Default implementation of the {@link com.health.service.TopicService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class TopicServiceImpl implements TopicService {
	 private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

	@Autowired
	private TopicRepository topicRepo;
	
	@Autowired 
	private TutorialRepository  tutRepo;
	
	/**
	 * @see com.health.service.TopicService#getNewTopicId()
	 */
	@Override
	public int getNewTopicId() {
		// TODO Auto-generated method stub
		try {
			return topicRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.TopicService#findBytopicName(String)
	 */
	@Override
	
	public Topic findBytopicName(String name) {
		// TODO Auto-generated method stub
		return topicRepo.findBytopicName(name);
	}

	/**
	 * @see com.health.service.TopicService#findById(int)
	 */
	@Override
	
	public Topic findById(int id) {
		// TODO Auto-generated method stub
		try {
			Optional<Topic> local=topicRepo.findById(id);
			logger.info("Fetching Topic from db by id {}", id);
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.health.service.TopicService#findAll()
	 */
	@Override
	public List<Topic> findAll() {
		// TODO Auto-generated method stub
		List<Topic> topics= (List<Topic>) topicRepo.findAll();
		Collections.sort(topics);
		return topics;
	}

	/**
	 * @see com.health.service.TopicService#save(Topic)
	 */
	@Override
	@CachePut(cacheNames = "topics", key = "#topic.id")
	public void save(Topic topic) {
		// TODO Auto-generated method stub
		topicRepo.save(topic);
	}
	
	@Override
	@Cacheable(cacheNames ="topics" )
	public List<Topic> getTopicsForcache() {
		System.out.println("Topic_check");
		List<Tutorial> tutorials = tutRepo.findAllByStatus(true);
		//List<Tutorial> tutorials = tutRepo.findAllByStatusTrue();
		Set<Topic> topicTemp = new HashSet<Topic>();
		for(Tutorial temp :tutorials) {
			
			Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
			if(c.isStatus()) {
				topicTemp.add(temp.getConAssignedTutorial().getTopicCatId().getTopic());
			}
		}
		List<Topic> topicTempSorted =new ArrayList<Topic>(topicTemp);
		Collections.sort(topicTempSorted);
		System.out.println("Topic_check_end");
		return topicTempSorted;
	}
}
