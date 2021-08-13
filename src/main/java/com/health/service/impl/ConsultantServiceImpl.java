package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.health.model.Consultant;
import com.health.model.User;
import com.health.repository.ConsultantRepository;

import com.health.service.ConsultantService;

/**
 * Default implementation of the {@link com.health.service.ConsultantService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class ConsultantServiceImpl implements ConsultantService {

	@Autowired
	private ConsultantRepository consultantRepo;

	/**
	 * @see com.health.service.ConsultantService#findAll()
	 */
	@Override
	public List<Consultant> findAll() {

		List<Consultant> local = (List<Consultant>) consultantRepo.findAll();

		return local;

	}
	
	/**
	 * @see com.health.service.ConsultantService#deleteProduct(Integer)
	 */
	@Override
	public void deleteProduct(Integer id){

		consultantRepo.deleteById(id);
																																																																												
	}

	
	/**
	 * @see com.health.service.ConsultantService#findById(int)
	 */
	@Override
	public Consultant findById(int id) {
		// TODO Auto-generated method stub
		try {
			Optional<Consultant> local=consultantRepo.findById(id);
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.health.service.ConsultantService#save(Consultant)
	 */
	@Override
	public void save(Consultant consult) {
		// TODO Auto-generated method stub
		
		consultantRepo.save(consult);
	}

	/**
	 * @see com.health.service.ConsultantService#getNewConsultantId()
	 */
	@Override
	public int getNewConsultantId() {
		// TODO Auto-generated method stub
		try {
			return consultantRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.ConsultantService#findByUser(User)
	 */
	@Override
	public Consultant findByUser(User usr) {
		// TODO Auto-generated method stub
		return consultantRepo.findByuser(usr);
	}

	/**
	 * @see com.health.service.ConsultantService#findByOnHome(boolean)
	 */
	@Override
	public List<Consultant> findByOnHome(boolean value) {
		// TODO Auto-generated method stub
		return consultantRepo.findByonHome(value);
	}

	
	
	
}
 

	
	

