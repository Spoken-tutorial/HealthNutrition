package com.health.service;

import java.util.List;

import com.health.model.IndianLanguage;

public interface IndianLanguageService {

    int getNewId();

    IndianLanguage findByName(String lanName);

    List<IndianLanguage> findAll();

}
