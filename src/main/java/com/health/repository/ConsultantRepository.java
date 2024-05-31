package com.health.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Consultant;
import com.health.model.User;

public interface ConsultantRepository extends CrudRepository<Consultant, Integer> {

    @Query("select max(consultantId) from Consultant")
    int getNewId();

    @CacheEvict(cacheNames = "consultants", allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(cacheNames = "consultants", allEntries = true)
    <S extends Consultant> S save(S entity);

    Consultant findByuser(User usr);

    List<Consultant> findByonHome(boolean value);

    @Override
    List<Consultant> findAll();

}
