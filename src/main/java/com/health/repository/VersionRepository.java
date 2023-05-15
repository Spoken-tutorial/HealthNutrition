package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Brochure;
import com.health.model.Version;

public interface VersionRepository  extends CrudRepository<Version, Integer>{
	
	@Query("select max(verId) from Version")
	int getNewId();
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	void deleteById(int id);
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	<S extends Version> S save(S entity);
	
	@Query("from Version where bro_id = ?1 and Brochure_Version=?2")
	Version findByBrochureAndBroVersion(Brochure brochure, int broVersion);
	
	@Query("from Version where bro_id = ?1")
	List<Version> findByBrochure(Brochure brochure);
	
	List<Version> findAll();

}
