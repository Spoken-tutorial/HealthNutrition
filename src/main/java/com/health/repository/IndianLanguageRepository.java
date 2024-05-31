package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.IndianLanguage;

public interface IndianLanguageRepository extends CrudRepository<IndianLanguage, Integer> {

    @Query("select max(id) from IndianLanguage")
    int getNewId();

    IndianLanguage findBylanName(String lanName);

}
