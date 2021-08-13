package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.domain.security.UserRole;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Language;
import com.health.model.TopicCategoryMapping;
import com.health.model.User;
import com.health.repository.ContributorAssignedTutorialRepository;
import com.health.service.ContributorAssignedTutorialService;

/**
 * Default implementation of the {@link com.health.service.ContributorAssignedTutorialService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class ContributorAssignedTutorialServiceImpl implements ContributorAssignedTutorialService{

	@Autowired
	private ContributorAssignedTutorialRepository conRepo;
	
	/**
	 * @see com.health.service.ContributorAssignedTutorialService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return conRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#findByTopicCatLan(List, List)
	 */
	@Override
	public List<ContributorAssignedTutorial> findByTopicCatLan(List<TopicCategoryMapping> topCat, List<UserRole> usrRole) {
		// TODO Auto-generated method stub
		List<ContributorAssignedTutorial> localData=new ArrayList<ContributorAssignedTutorial>();
		
		for(TopicCategoryMapping temp:topCat) {
			for(UserRole xlan : usrRole) {
				localData.add(conRepo.findByTopicCatLan(temp, xlan.getLanguage()));
			}
			
		}
		
		return localData;
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#findAllByTopicCat(List)
	 */
	@Override
	public List<ContributorAssignedTutorial> findAllByTopicCat(List<TopicCategoryMapping> topCat) {
		// TODO Auto-generated method stub
		return conRepo.findByTopicCat(topCat);
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#findAllByTopicCatAndLanViewPart(List, Language)
	 */
	@Override
	public List<ContributorAssignedTutorial> findAllByTopicCatAndLanViewPart(List<TopicCategoryMapping> topCat,Language lan) {
		// TODO Auto-generated method stub
		List<ContributorAssignedTutorial> localData=new ArrayList<ContributorAssignedTutorial>();
		
		for(TopicCategoryMapping temp:topCat) {
			
			localData.add(conRepo.findByTopicCatLan(temp, lan));
			
		}
		
		return localData;
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#findByTopicCatAndLanViewPart(TopicCategoryMapping, Language)
	 */
	@Override
	public ContributorAssignedTutorial findByTopicCatAndLanViewPart(TopicCategoryMapping topCat, Language lan) {
		// TODO Auto-generated method stub
		return conRepo.findByTopicCatLan(topCat, lan);
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#findAllByTopicCat(List)
	 */
	@Override
	public List<ContributorAssignedTutorial> findByTopicCat(TopicCategoryMapping topCat) {
		// TODO Auto-generated method stub
		return conRepo.findAllBytopicCatId(topCat);
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#findAllByLan(Language)
	 */
	@Override
	public List<ContributorAssignedTutorial> findAllByLan(Language lan) {
		// TODO Auto-generated method stub
		return conRepo.findAllBylan(lan);
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#findAll()
	 */
	@Override
	public List<ContributorAssignedTutorial> findAll() {
		// TODO Auto-generated method stub
		return (List<ContributorAssignedTutorial>) conRepo.findAll();
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#save(ContributorAssignedTutorial)
	 */
	@Override
	public void save(ContributorAssignedTutorial con) {
		// TODO Auto-generated method stub
		conRepo.save(con);
	}

	/**
	 * @see com.health.service.ContributorAssignedTutorialService#findById(int)
	 */
	@Override
	public ContributorAssignedTutorial findById(int id) {
		// TODO Auto-generated method stub
		return conRepo.findById(id).get();
	}

}
