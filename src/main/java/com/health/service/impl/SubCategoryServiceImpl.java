package com.health.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.SubCategory;
import com.health.repository.SubCategoryRepository;
import com.health.service.SubCategoryService;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository repo;

    @Override
    public SubCategory findBySubCategoryName(String subCatName) {

        return repo.findBySubCategoryName(subCatName);
    }

    @Override
    public SubCategory findBySubCategoryId(int subCatId) {

        return repo.findBySubCategoryId(subCatId);
    }

}
