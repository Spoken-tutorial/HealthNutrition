package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Consultant;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related
 * to Consultant object
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface ConsultantRepository extends CrudRepository<Consultant, Integer> {

    /**
     * Find the next unique id for the object
     * 
     * @return primitive integer value
     */
    @Query("select max(consultantId) from Consultant")
    int getNewId();

    @CacheEvict(cacheNames = "consultants", allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(cacheNames = "consultants", allEntries = true)
    <S extends Consultant> S save(S entity);

    /**
     * find Consultant object given user object
     * 
     * @param usr user object
     * @return Consultant object
     */
    Consultant findByuser(User usr);

    /**
     * find list of Consultant object given onHome field
     * 
     * @param valuee boolean
     * @return list of Consultant object
     */
    // @Cacheable(cacheNames ="consultants" )
    List<Consultant> findByonHome(boolean value);

    // @Cacheable(cacheNames ="consultants" )
    @Override
    List<Consultant> findAll();

}
