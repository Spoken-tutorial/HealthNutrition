package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.Tutorial;
import com.health.repository.TutorialRepository;
import com.health.service.TutorialService;

/**
 * Default implementation of the {@link com.health.service.TutorialService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class TutorialServiceImpl implements TutorialService {

	@Autowired
	private TutorialRepository tutorialRepo;

	/**
	 * @see com.health.service.TutorialService#findAll()
	 */
	@Override
	public List<Tutorial> findAll() {
		// TODO Auto-generated method stub
		return (List<Tutorial>) tutorialRepo.findAll();
	}

	/**
	 * @see com.health.service.TutorialService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return tutorialRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.TutorialService#findAllByContributorAssignedTutorial(ContributorAssignedTutorial)
	 */
	@Override
	public List<Tutorial> findAllByContributorAssignedTutorial(ContributorAssignedTutorial con) {
		// TODO Auto-generated method stub
		return tutorialRepo.findAllByconAssignedTutorial(con);
	}

	/**
	 * @see com.health.service.TutorialService#save(Tutorial)
	 */
	@Override
	public void save(Tutorial tut) {
		// TODO Auto-generated method stub
		tutorialRepo.save(tut);
	}

	/**
	 * @see com.health.service.TutorialService#getById(int)
	 */
	@Override
	public Tutorial getById(int id) {
		// TODO Auto-generated method stub
		try {
			Optional<Tutorial> local=tutorialRepo.findById(id);
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.health.service.TutorialService#findAllByContributorAssignedTutorialList(List)
	 */
	@Override
	public List<Tutorial> findAllByContributorAssignedTutorialList(List<ContributorAssignedTutorial> con) {
		// TODO Auto-generated method stub
		List<Tutorial> localList=new ArrayList<Tutorial>();
		for(ContributorAssignedTutorial conTemp:con) {
			localList.addAll(tutorialRepo.findAllByconAssignedTutorial(conTemp));
		}
		return localList;

	}

	/**
	 * @see com.health.service.TutorialService#findAllBystatus(boolean)
	 */
	@Override
	public List<Tutorial> findAllBystatus(boolean status) {
		// TODO Auto-generated method stub
		return tutorialRepo.findAllBystatus(status);
	}

	/**
	 * @see com.health.service.TutorialService#findAllPagination(Pageable)
	 */
	@Override
	public Page<Tutorial> findAllPagination(Pageable page) {
		// TODO Auto-generated method stub
		return tutorialRepo.findAll(page);
	}

	/**
	 * @see com.health.service.TutorialService#findAllByconAssignedTutorialPagination(ContributorAssignedTutorial, Pageable)
	 */
	@Override
	public Page<Tutorial> findAllByconAssignedTutorialPagination(ContributorAssignedTutorial con, Pageable page) {
		// TODO Auto-generated method stub
		return tutorialRepo.findAllByconAssignedTutorialPagination(con, page);
		
	}

	@Override
	public Page<Tutorial> findAllByconAssignedTutorialListPagination(List<ContributorAssignedTutorial> con,
			Pageable page) {
		// TODO Auto-generated method stub
		return tutorialRepo.findAllByconAssignedTutorialListPagination(con, page);
	}




}