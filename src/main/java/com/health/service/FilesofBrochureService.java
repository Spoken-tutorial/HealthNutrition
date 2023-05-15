package com.health.service;

import java.util.List;

import com.health.model.FilesofBrochure;
import com.health.model.Version;
import com.health.model.Language;


public interface FilesofBrochureService {
	
	/**
	 * Find the next unique id for brochure object
	 * @return primitive integer value
	 */
	int getNewId();

	
	void save( FilesofBrochure filesofBrochure);

	
	List<FilesofBrochure> findAll();

	
	void delete(FilesofBrochure temp);

	
	FilesofBrochure findById(int id);

	
	List<FilesofBrochure> findByVersion(Version ver);
	
	List<FilesofBrochure> findByLanguage(Language lan);
	
	FilesofBrochure findByLanguageandVersion(Language lan, Version ver);
	
	List<String> findAlllangNames(Version ver);
	
	String GetWebFileofFirstLan(Version ver);
	
	String GetPrintFileofFirstLan(Version ver);
	
	void  saveAll(List<FilesofBrochure> filesofbrochure);
	

}
