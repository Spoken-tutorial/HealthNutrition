package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.FilesofBrouchure;
import com.health.model.Language;
import com.health.model.Version;

public interface FilesofBrouchureRepository extends CrudRepository<FilesofBrouchure, Integer> {

    /**
     * Find the next unique id for the object
     * 
     * @return primitive integer value
     */
    @Query("select max(broFileId) from FilesofBrouchure")
    int getNewId();

    FilesofBrouchure findByLan(Language lan);

    void deleteByBroFileId(int broFileId);

    @Query("from FilesofBrouchure where version = ?1")
    List<FilesofBrouchure> findByVersion(Version version);

    @Query("from FilesofBrouchure where lan = ?1")
    List<FilesofBrouchure> findByLanguage(Language lan);

    @Query("from FilesofBrouchure where lan = ?1 and version=?2")
    FilesofBrouchure findByLanguageAndVersion(Language lan, Version version);

    @Override
    List<FilesofBrouchure> findAll();

}
