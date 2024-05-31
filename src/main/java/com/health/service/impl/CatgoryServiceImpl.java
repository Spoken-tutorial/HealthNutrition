package com.health.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Category;
import com.health.model.Tutorial;
import com.health.repository.CategoryRepository;
import com.health.repository.TutorialRepository;
import com.health.service.CategoryService;

@Service
public class CatgoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CatgoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private TutorialRepository tutRepo;

    @Override
    public List<Category> findAll() {

        List<Category> cats = categoryRepo.findAll();
        Collections.sort(cats);
        return cats;

    }

    @Override

    public Category findBycategoryname(String name) {

        try {
            Category local = categoryRepo.findBycatName(name);

            return local;
        } catch (Exception e) {

            logger.error("Category Name error in Category Service Impl class: {}", name, e);
            return null;
        }

    }

    @Override

    public void deleteProduct(Integer id) {

        categoryRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Boolean updateCategory(String testimonialName, int id) {

        return false;

    }

    @Override

    public Category findByid(int id) {

        try {
            Optional<Category> var = categoryRepo.findById(id);

            return var.get();
        } catch (Exception e) {

            logger.error("find by Id error in Category Service Impl class: {}", id, e);
            return null;
        }

    }

    @Override
    public int getNewCatId() {

        try {
            return categoryRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Category Service Impl class: {}", categoryRepo.getNewId(), e);
            return 1;
        }

    }

    @Override

    public Category save(Category cat) {

        categoryRepo.save(cat);
        return null;
    }

    @Override
    public List<Category> findAllByOrder() {

        return categoryRepo.findAllByOrderBy();

    }

    @Override

    public List<Category> findAllCategoryByOrderForCache() {

        return categoryRepo.findAllByOrderBy();
    }

    @Override
    @Cacheable(cacheNames = "categories")
    public List<Category> getCategoriesForCache() {

        List<Tutorial> tutorials = tutRepo.findAllByStatus(true);
        Set<Category> catTemp = new HashSet<Category>();
        for (Tutorial temp : tutorials) {

            Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
            if (c.isStatus()) {
                catTemp.add(c);
            }

        }

        List<Category> catTempSorted = new ArrayList<Category>(catTemp);
        Collections.sort(catTempSorted);

        return catTempSorted;
    }

}
