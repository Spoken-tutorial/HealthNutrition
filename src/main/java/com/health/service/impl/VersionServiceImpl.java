package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Brouchure;
import com.health.model.Version;
import com.health.repository.VersionRepository;
import com.health.service.VersionService;

@Service
public class VersionServiceImpl implements VersionService{
	
	@Autowired
	private VersionRepository verRepository;
	
	@Override
	public int getNewId() {
		try {
			return verRepository.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	public void save(Version ver) {
		verRepository.save(ver);
		
	}

	@Override
	
	public List<Version> findAll(){
		return verRepository.findAll();
		
	}

	@Override
	public void delete(Version ver) {
		
		verRepository.delete(ver);
	}

	@Override
	public Version findById(int id) {
		
		try {
			return verRepository.findById(id).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	@Override
	public Version findByBrouchureAndPrimaryVersion(Brouchure brochure, int primaryVersion) {
		try {
			return verRepository.findByBrouchureAndBroVersion(brochure, primaryVersion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Version> findByCategory(Brouchure bro){
		
		return verRepository.findByBrouchure(bro);
		
	}

}
