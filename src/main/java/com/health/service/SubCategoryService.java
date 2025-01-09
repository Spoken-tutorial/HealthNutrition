package com.health.service;

import com.health.model.SubCategory;

public interface SubCategoryService {

    SubCategory findBySubCategoryName(String subCatName);

    SubCategory findBySubCategoryId(int subCatId);

}
