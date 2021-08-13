package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Comment;
import com.health.model.Tutorial;
import com.health.model.User;
import com.health.repository.CommentRepository;
import com.health.service.CommentService;

/**
 * Default implementation of the {@link com.health.service.CommentService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository comRepo;

	/**
	 * @see com.health.service.CommentService#getNewCommendId()
	 */
	@Override
	public int getNewCommendId() {
		// TODO Auto-generated method stub
		try {
			return comRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.CommentService#save(Comment)
	 */
	@Override
	public void save(Comment com) {
		// TODO Auto-generated method stub
		comRepo.save(com);
	}

	/**
	 * @see com.health.service.CommentService#getCommentBasedOnUserTutorialType(String, User, Tutorial, String)
	 */
	@Override
	public List<Comment> getCommentBasedOnUserTutorialType(String type, User usr, Tutorial tut,String role) {
		// TODO Auto-generated method stub
		return comRepo.getCommentBasedOnUserTutorialType(type, usr, tut,role);
	}

	/**
	 * @see com.health.service.CommentService#getCommentBasedOnTutorialType(String, Tutorial)
	 */
	@Override
	public List<Comment> getCommentBasedOnTutorialType(String type, Tutorial tut) {
		// TODO Auto-generated method stub
		return comRepo.getCommentBasedOnTutorialType(type, tut);
	}
}

