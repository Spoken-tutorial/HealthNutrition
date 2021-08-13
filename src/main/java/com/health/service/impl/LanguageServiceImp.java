package com.health.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Category;
import com.health.model.Language;
import com.health.model.User;
import com.health.repository.LangaugeRepository;
import com.health.service.LanguageService;

/**
 * Default implementation of the {@link com.health.service.LanguageService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class LanguageServiceImp implements LanguageService
{

	@Autowired
	private LangaugeRepository languageRepo;

	/**
	 * @see com.health.service.LanguageService#getAllLanguages()
	 */
	@Override
	public List<Language> getAllLanguages() {
		// TODO Auto-generated method stub
		List<Language> lans= languageRepo.findAll();
		Collections.sort(lans);
		return lans;
		
	}

	/**
	 * @see com.health.service.LanguageService#getnewLanId()
	 */
	@Override
	public int getnewLanId() {
		// TODO Auto-generated method stub
		try {
			return languageRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.LanguageService#getByLanName(String)
	 */
	@Override
	public Language getByLanName(String langName) {
		// TODO Auto-generated method stub
		try {
			return languageRepo.findBylangName(langName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * @see com.health.service.LanguageService#getById(int)
	 */
	@Override
	public Language getById(int lanId) {
		// TODO Auto-generated method stub
		try {
			Optional<Language> local=languageRepo.findById(lanId);
			
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.health.service.LanguageService#save(Language)
	 */
	@Override
	public void save(Language lan) {
		// TODO Auto-generated method stub
		languageRepo.save(lan);
		
	}
	
	
	
	
}