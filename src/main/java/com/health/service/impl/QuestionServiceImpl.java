package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Language;
import com.health.model.Question;
import com.health.model.TopicCategoryMapping;
import com.health.repository.QuestionRepository;
import com.health.service.QuestionService;

/**
 * Default implementation of the {@link com.health.service.QuestionService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	private QuestionRepository questionRepo;
	
	/**
	 * @see com.health.service.QuestionService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return questionRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.QuestionService#save(Question)
	 */
	@Override
	public void save(Question ques) {
		// TODO Auto-generated method stub
		questionRepo.save(ques);
	}

	/**
	 * @see com.health.service.QuestionService#findById(int)
	 */
	@Override
	public Question findById(int id) {
		// TODO Auto-generated method stub
		try {
			Optional<Question> local=questionRepo.findById(id);
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.health.service.QuestionService#getQuestionBasedOnTopicCatAndLan(TopicCategoryMapping, Language)
	 */
	@Override
	public Question getQuestionBasedOnTopicCatAndLan(TopicCategoryMapping topicCat, Language lan) {
		// TODO Auto-generated method stub
		return questionRepo.findByTopicLan(topicCat, lan);
	}

	/**
	 * @see com.health.service.QuestionService#findAll()
	 */
	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return (List<Question>) questionRepo.findAll();
	}

	
}
