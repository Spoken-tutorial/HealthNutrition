package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.FeedbackForm;
import com.health.repository.FeedBackRepository;
import com.health.repository.FeedbackContactFormRepository;
import com.health.service.FeedbackService;

/**
 * Default implementation of the {@link com.health.service.FeedbackService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackContactFormRepository fRepo;

	/**
	 * @see com.health.service.FeedbackService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return fRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.FeedbackService#getNewId()
	 */
	@Override
	public void save(FeedbackForm ff) {
		// TODO Auto-generated method stub
		
		fRepo.save(ff);
	}

	/**
	 * @see com.health.service.FeedbackService#findAll()
	 */
	@Override
	public List<FeedbackForm> findAll() {
		// TODO Auto-generated method stub
		return (List<FeedbackForm>) fRepo.findAll();
	}
	
	
}
