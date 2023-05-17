package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Brouchure;
import com.health.model.Version;

public interface VersionRepository  extends CrudRepository<Version, Integer>{
	
	@Query("select max(verId) from Version")
	int getNewId();
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	void deleteById(int id);
	
	@CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
	<S extends Version> S save(S entity);
	
	@Query("from Version where bro_id = ?1 and Brouchure_Version=?2")
	Version findByBrouchureAndBroVersion(Brouchure brouchure, int broVersion);
	
	@Query("from Version where bro_id = ?1")
	List<Version> findByBrouchure(Brouchure brouchure);
	
	List<Version> findAll();

}
