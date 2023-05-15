package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Brochure;
import com.health.model.Category;
import com.health.model.TopicCategoryMapping;
import com.health.repository.BrochureRepository;
import com.health.repository.TopicCategoryMappingRepository;
import com.health.service.BrochureService;

/**
 * Default implementation of the {@link com.health.service.BrochureService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class BrochureServiceImpl implements BrochureService{

	@Autowired
	private BrochureRepository repo;

	@Autowired
	private TopicCategoryMappingRepository topicCatRepo;

	/**
	 * @see com.health.service.BrochureService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return repo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.BrochureService#save(Brochure)
	 */
	@Override
	public void save(Brochure temp) {
		// TODO Auto-generated method stub
		repo.save(temp);
	}

	/**
	 * @see com.health.service.BrochureService#findAll()
	 */
	@Override
	//@Cacheable(cacheNames ="brochures" )
	public List<Brochure> findAll() {
		return repo.findAll();
	}

	/**
	 * @see com.health.service.BrochureService#delete(Brochure)
	 */
	@Override
	public void delete(Brochure temp) {
		// TODO Auto-generated method stub
		repo.delete(temp);
	}

	/**
	 * @see com.health.service.BrochureService#findByOnHome(boolean)
	 */
	@Override
	public List<Brochure> findByOnHome(boolean value) {
		// TODO Auto-generated method stub
		return repo.findAllByshowOnHomepage(value);
	}

	/**
	 * @see com.health.service.BrochureService#findById(int)
	 */
	@Override
	public Brochure findById(int id) {
		// TODO Auto-generated method stub
		try {
			return repo.findById(id).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.health.service.impl.BrochureServiceImpl#findByCategory(Category)
	 */
	@Override
	public List<Brochure> findByCategory(Category cat) {
		// TODO Auto-generated method stub
		List<TopicCategoryMapping> topicCat = topicCatRepo.findAllBycat(cat);
		List<Brochure> brochures = new ArrayList<Brochure>();
		for (TopicCategoryMapping t:topicCat) {
			brochures.addAll(repo.findByTopicCat(t));
		}

		return brochures;
	}
	
	@Override
	@Cacheable(cacheNames ="brochures" )
	public List<Brochure> findAllBrochuresForCache(){
		System.out.println("BrochureCheck");
		return repo.findAllByshowOnHomepage(true);
		
	}

}
