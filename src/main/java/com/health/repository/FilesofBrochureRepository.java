package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Language;
import com.health.model.FilesofBrochure;
import com.health.model.Version;

public interface FilesofBrochureRepository extends CrudRepository<FilesofBrochure, Integer> {

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(broFileId) from FilesofBrochure")
	int getNewId();
	
	
	FilesofBrochure findByLan(Language lan);

	
	void deleteByBroFileId(int broFileId);
	
	
	@Query("from FilesofBrochure where version = ?1")
	List<FilesofBrochure > findByVersion(Version version);
	
	@Query("from FilesofBrochure where lan = ?1")
	List<FilesofBrochure > findByLanguage(Language lan);
	
	@Query("from FilesofBrochure where lan = ?1 and version=?2")
	FilesofBrochure  findByLanguageAndVersion(Language lan, Version version);
	
	
	List<FilesofBrochure> findAll();
	

	
}
