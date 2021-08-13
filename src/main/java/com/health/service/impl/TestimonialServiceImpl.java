package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Testimonial;

import com.health.repository.TestimonialRepository;
import com.health.service.TestimonialService;

/**
 * Default implementation of the {@link com.health.service.TestimonialService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class TestimonialServiceImpl implements  TestimonialService {

	@Autowired
	private TestimonialRepository testRepo;
	
	/**
	 * @see com.health.service.TestimonialService#findAll()
	 */
	@Override
	public List<Testimonial> findAll() {
		// TODO Auto-generated method stub
		List<Testimonial> test=(List<Testimonial>) testRepo.findAll();
		return test;
	}

	/**
	 * @see com.health.service.TestimonialService#deleteProduct(Integer)
	 */
	@Override
	public void deleteProduct(Integer id) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.health.service.TestimonialService#findById(int)
	 */
	@Override
	public Testimonial findById(int id) {
		// TODO Auto-generated method stub
		try {
			Optional<Testimonial> local=testRepo.findById(id);
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * @see com.health.service.TestimonialService#getNewTestimonialId()
	 */
	@Override
	public int getNewTestimonialId() {
		// TODO Auto-generated method stub
		try {
			return testRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.TestimonialService#save(Testimonial)
	 */
	@Override
	public void save(Testimonial temp) {
		// TODO Auto-generated method stub
		
		testRepo.save(temp);
	}

	/**
	 * @see com.health.service.TestimonialService#findByApproved(boolean)
	 */
	@Override
	public List<Testimonial> findByApproved(boolean value) {
		// TODO Auto-generated method stub
		return testRepo.findByapproved(value);
	}
	
	
}
