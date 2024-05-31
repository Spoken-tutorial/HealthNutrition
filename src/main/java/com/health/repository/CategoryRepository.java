package com.health.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.health.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select max(categoryId) from Category")
    int getNewId();

    @CacheEvict(cacheNames = "categories", allEntries = true)
    void deleteById(int id);

    @Override
    @CacheEvict(value = { "categories", "topics", "tutorials", "languages" }, allEntries = true)
    <S extends Category> S save(S entity);

    Category findBycatName(String catname);

    Optional<Category> findById(int id);

    @Query(value = "select category from Category category order by category.catName")
    List<Category> findAllByOrderBy();

}