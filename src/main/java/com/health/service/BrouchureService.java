package com.health.service;

import java.util.List;

import com.health.model.Brouchure;
import com.health.model.Category;

public interface BrouchureService {

    int getNewId();

    void save(Brouchure temp);

    List<Brouchure> findAll();

    void delete(Brouchure temp);

    List<Brouchure> findByOnHome(boolean value);

    Brouchure findById(int id);

    List<Brouchure> findByCategory(Category cat);

    List<Brouchure> findAllBrouchuresForCache();

}
