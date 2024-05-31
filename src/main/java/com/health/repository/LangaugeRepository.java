package com.health.repository;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.health.model.Language;

public interface LangaugeRepository extends JpaRepository<Language, Integer> {

    @Query("select max(lanId) from Language")
    int getNewId();

    Language findBylangName(String langName);

    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    void deleteById(int lanId);

    @Override
    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    <S extends Language> S save(S entity);

    Optional<Language> findById(int lanId);

}
