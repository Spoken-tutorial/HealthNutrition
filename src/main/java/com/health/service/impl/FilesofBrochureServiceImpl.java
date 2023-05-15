package com.health.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.health.service.FilesofBrochureService;
import com.health.model.FilesofBrochure;
import com.health.model.Language;
import com.health.model.Version;
import com.health.repository.FilesofBrochureRepository;

@Service
public class FilesofBrochureServiceImpl implements  FilesofBrochureService {
	
	@Autowired
	private FilesofBrochureRepository filesofbrochureRepository;
	
	@Override
	public int getNewId() {
		try {
			return filesofbrochureRepository.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	public void save( FilesofBrochure filesofBrochure) {
		filesofbrochureRepository.save(filesofBrochure);
		
	}

	@Override
	public List<FilesofBrochure> findAll(){
		return filesofbrochureRepository.findAll();
		
	}

	@Override
	public void delete(FilesofBrochure temp) {
		filesofbrochureRepository.delete(temp);
	}

	@Override
	public FilesofBrochure findById(int id) {
		try {
			return filesofbrochureRepository.findById(id).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<FilesofBrochure> findByVersion(Version ver){
		return filesofbrochureRepository.findByVersion(ver);
		
	}
	
	@Override
	public List<FilesofBrochure> findByLanguage(Language lan){
		return filesofbrochureRepository.findByLanguage(lan);
	}
	
	
	@Override
	public List<String> findAlllangNames(Version ver){
		List<FilesofBrochure> listofBroFiles=filesofbrochureRepository.findByVersion(ver);
		List<String> langNames=new ArrayList<>();
		
		for(FilesofBrochure temp: listofBroFiles) {
		
			 langNames.add(temp.getLan().getLangName());
		}
		return langNames;
	}
	
	@Override
	public String GetWebFileofFirstLan(Version ver) {
		
		List<FilesofBrochure> listofBroFiles=filesofbrochureRepository.findByVersion(ver);
		FilesofBrochure fileBro = listofBroFiles.get(0);
		return fileBro.getWebPath();
		
	}
	
	@Override
	public String GetPrintFileofFirstLan(Version ver) {
		
		List<FilesofBrochure> listofBroFiles=filesofbrochureRepository.findByVersion(ver);
		FilesofBrochure fileBro = listofBroFiles.get(0);
		return fileBro.getPrintPath();
		
	}
	
	@Override
	public FilesofBrochure findByLanguageandVersion(Language lan, Version ver) {
		
		return filesofbrochureRepository.findByLanguageAndVersion(lan, ver);
	}
	
	
	@Override
	public void saveAll(List<FilesofBrochure> filesofbrochure) {
		filesofbrochureRepository.saveAll(filesofbrochure);
		
	}
	
	
	

}
