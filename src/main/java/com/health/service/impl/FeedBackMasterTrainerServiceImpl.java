package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.FeedbackMasterTrainer;
import com.health.model.User;
import com.health.repository.FeedBackRepository;
import com.health.service.FeedBackMasterTrainerService;

/**
 * Default implementation of the {@link com.health.service.FeedBackMasterTrainerService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class FeedBackMasterTrainerServiceImpl implements FeedBackMasterTrainerService {

	@Autowired
	private FeedBackRepository feedRepo;

	/**
	 * @see com.health.service.FeedBackMasterTrainerService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return feedRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.FeedBackMasterTrainerService#save(FeedbackMasterTrainer)
	 */
	@Override
	public void save(FeedbackMasterTrainer temp) {
		// TODO Auto-generated method stub
		feedRepo.save(temp);
	}

	/**
	 * @see com.health.service.FeedBackMasterTrainerService#findByUser(User)
	 */
	@Override
	public List<FeedbackMasterTrainer> findByUser(User user) {

		return feedRepo.findByUser(user);
	}

}
