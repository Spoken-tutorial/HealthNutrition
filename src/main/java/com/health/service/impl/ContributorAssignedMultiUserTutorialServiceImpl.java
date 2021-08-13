package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.User;
import com.health.repository.ContributorAssignedMultiUserTutorialRepository;
import com.health.service.ContributorAssignedMultiUserTutorialService;

/**
 * Default implementation of the {@link com.health.service.ContributorAssignedMultiUserTutorialService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class ContributorAssignedMultiUserTutorialServiceImpl implements ContributorAssignedMultiUserTutorialService {

	@Autowired
	private ContributorAssignedMultiUserTutorialRepository conRepo;

	/**
	 * @see com.health.service.ContributorAssignedMultiUserTutorialService#save(ContributorAssignedMultiUserTutorial)
	 */
	@Override
	public void save(ContributorAssignedMultiUserTutorial con) {
		// TODO Auto-generated method stub
		conRepo.save(con);
	}

	/**
	 * @see com.health.service.ContributorAssignedMultiUserTutorialService#getNewId()
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
	 * @see com.health.service.ContributorAssignedMultiUserTutorialService#getAllByuser(User)
	 */
	@Override
	public List<ContributorAssignedMultiUserTutorial> getAllByuser(User usr) {
		// TODO Auto-generated method stub
		return conRepo.findAllByuser(usr);
	}
	
	
}
