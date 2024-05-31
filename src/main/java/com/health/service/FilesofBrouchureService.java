package com.health.service;

import java.util.List;

import com.health.model.FilesofBrouchure;
import com.health.model.Language;
import com.health.model.Version;

public interface FilesofBrouchureService {

    int getNewId();

    void save(FilesofBrouchure filesofBrouchure);

    List<FilesofBrouchure> findAll();

    void delete(FilesofBrouchure temp);

    FilesofBrouchure findById(int id);

    List<FilesofBrouchure> findByVersion(Version ver);

    List<FilesofBrouchure> findByLanguage(Language lan);

    FilesofBrouchure findByLanguageandVersion(Language lan, Version ver);

    List<String> findAlllangNames(Version ver);

    String GetWebFileofFirstLan(Version ver);

    void saveAll(List<FilesofBrouchure> filesofbrouchure);

}
