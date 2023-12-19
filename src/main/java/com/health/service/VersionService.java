package com.health.service;

import java.util.List;

import com.health.model.Brouchure;
import com.health.model.Version;

public interface VersionService {

    int getNewId();

    void save(Version ver);

    List<Version> findAll();

    void delete(Version ver);

    Version findById(int id);

    Version findByBrouchureAndPrimaryVersion(Brouchure brochure, int primaryVersion);

    List<Version> findByCategory(Brouchure bro);

}
