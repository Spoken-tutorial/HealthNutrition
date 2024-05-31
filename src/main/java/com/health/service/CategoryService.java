package com.health.service;

import java.util.List;

import com.health.model.Category;

public interface CategoryService {

    List<Category> findAll();

    Category findBycategoryname(String name);

    void deleteProduct(Integer id);

    Category findByid(int id);

    Boolean updateCategory(String description, int id);

    int getNewCatId();

    Category save(Category cat);

    List<Category> findAllByOrder();

    List<Category> findAllCategoryByOrderForCache();

    List<Category> getCategoriesForCache();

}
