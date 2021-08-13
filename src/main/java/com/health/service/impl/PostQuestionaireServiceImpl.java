package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.PostQuestionaire;
import com.health.model.User;
import com.health.repository.PostQuestionaireRepository;
import com.health.service.PostQuestionaireService;

/**
 * Default implementation of the {@link com.health.service.PostQuestionaireService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class PostQuestionaireServiceImpl implements PostQuestionaireService {

	@Autowired
	private PostQuestionaireRepository repo;

	/**
	 * @see com.health.service.PostQuestionaireService#getNewCatId()
	 */
	@Override
	public int getNewCatId() {
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
	 * @see com.health.service.PostQuestionaireService#save(PostQuestionaire)
	 */
	@Override
	public void save(PostQuestionaire temp) {
		// TODO Auto-generated method stub
		repo.save(temp);
	}

	/**
	 * @see com.health.service.PostQuestionaireService#findAll()
	 */
	@Override
	public List<PostQuestionaire> findAll() {
		// TODO Auto-generated method stub
		return (List<PostQuestionaire>) repo.findAll();
	}

	/**
	 * @see com.health.service.PostQuestionaireService#findByUser(User)
	 */
	@Override
	public List<PostQuestionaire> findByUser(User user) {

		return repo.findByUser(user);
	}
}
