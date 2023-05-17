package com.health.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.health.service.FilesofBrouchureService;
import com.health.model.FilesofBrouchure;
import com.health.model.Language;
import com.health.model.Version;
import com.health.repository.FilesofBrouchureRepository;

@Service
public class FilesofBrouchureServiceImpl implements  FilesofBrouchureService {
	
	@Autowired
	private FilesofBrouchureRepository filesofbrouchureRepository;
	
	@Override
	public int getNewId() {
		try {
			return filesofbrouchureRepository.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	public void save( FilesofBrouchure filesofBrouchure) {
		filesofbrouchureRepository.save(filesofBrouchure);
		
	}

	@Override
	public List<FilesofBrouchure> findAll(){
		return filesofbrouchureRepository.findAll();
		
	}

	@Override
	public void delete(FilesofBrouchure temp) {
		filesofbrouchureRepository.delete(temp);
	}

	@Override
	public FilesofBrouchure findById(int id) {
		try {
			return filesofbrouchureRepository.findById(id).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<FilesofBrouchure> findByVersion(Version ver){
		return filesofbrouchureRepository.findByVersion(ver);
		
	}
	
	@Override
	public List<FilesofBrouchure> findByLanguage(Language lan){
		return filesofbrouchureRepository.findByLanguage(lan);
	}
	
	
	@Override
	public List<String> findAlllangNames(Version ver){
		List<FilesofBrouchure> listofBroFiles=filesofbrouchureRepository.findByVersion(ver);
		List<String> langNames=new ArrayList<>();
		
		for(FilesofBrouchure temp: listofBroFiles) {
		
			 langNames.add(temp.getLan().getLangName());
		}
		return langNames;
	}
	
	@Override
	public String GetWebFileofFirstLan(Version ver) {
		
		List<FilesofBrouchure> listofBroFiles=filesofbrouchureRepository.findByVersion(ver);
		FilesofBrouchure fileBro = listofBroFiles.get(0);
		return fileBro.getWebPath();
		
	}
	
	@Override
	public String GetPrintFileofFirstLan(Version ver) {
		
		List<FilesofBrouchure> listofBroFiles=filesofbrouchureRepository.findByVersion(ver);
		FilesofBrouchure fileBro = listofBroFiles.get(0);
		return fileBro.getPrintPath();
		
	}
	
	@Override
	public FilesofBrouchure findByLanguageandVersion(Language lan, Version ver) {
		
		return filesofbrouchureRepository.findByLanguageAndVersion(lan, ver);
	}
	
	
	@Override
	public void saveAll(List<FilesofBrouchure> filesofbrouchure) {
		filesofbrouchureRepository.saveAll(filesofbrouchure);
		
	}
	
	
	

}
