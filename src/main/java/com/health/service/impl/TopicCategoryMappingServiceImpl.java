package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;
import com.health.repository.TopicCategoryMappingRepository;
import com.health.service.TopicCategoryMappingService;

/**
 * Default implementation of the {@link com.health.service.TopicCategoryMappingService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class TopicCategoryMappingServiceImpl implements TopicCategoryMappingService {

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
			return topicCatRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.TopicCategoryMappingService#findAllByCategoryBasedOnUserRoles(List)
	 */
	@Override
	public List<TopicCategoryMapping> findAllByCategoryBasedOnUserRoles(List<UserRole> userRoles) {
		// TODO Auto-generated method stub

		List<TopicCategoryMapping> temp = new ArrayList<TopicCategoryMapping>();
		for(UserRole x : userRoles) {

//			System.out.println("********************************");
//			System.out.println(topicCatRepo.findAllBycat(x.getCategory()).get(0));

			temp.addAll(topicCatRepo.findAllBycat(x.getCategory()));
		}

		return temp;
	}

	/**
	 * @see com.health.service.TopicCategoryMappingService#findAllByCategoryAndTopic(Category, Topic)
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

	/**
	 * @see com.health.service.TopicCategoryMappingService#findByCategoryAndOrder(Category, int)
	 */
	@Override
	public TopicCategoryMapping findByCategoryAndOrder(Category cat, int order) {
		// TODO Auto-generated method stub
		return topicCatRepo.findBycatAndorder(cat, order);
	}


}
