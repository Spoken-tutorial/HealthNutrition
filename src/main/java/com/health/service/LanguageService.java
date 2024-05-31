package com.health.service;

import java.util.List;

import com.health.model.Language;

public interface LanguageService {

    List<Language> getAllLanguages();

    int getnewLanId();

    Language getByLanName(String langName);

    Language getById(int lanId);

    void save(Language lan);

    List<Language> getLanguagesForCache();

}
