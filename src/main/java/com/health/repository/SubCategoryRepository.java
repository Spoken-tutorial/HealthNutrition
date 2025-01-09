package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    SubCategory findBySubCategoryName(String subCatName);

    SubCategory findBySubCategoryId(int subCatId);

}
