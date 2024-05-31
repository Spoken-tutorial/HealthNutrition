package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.UserIndianLanguageMapping;

public interface UserIndianLanguageMappingRepository extends CrudRepository<UserIndianLanguageMapping, Integer> {

    @Query("select max(id) from UserIndianLanguageMapping")
    int getNewId();

}
