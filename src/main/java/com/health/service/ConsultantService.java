package com.health.service;

import java.util.List;

import com.health.model.Consultant;
import com.health.model.User;

public interface ConsultantService {

    List<Consultant> findAll();

    void deleteProduct(Integer id);

    Consultant findById(int id);

    void save(Consultant consult);

    int getNewConsultantId();

    Consultant findByUser(User usr);

    List<Consultant> findByOnHome(boolean value);

    List<Consultant> findAllConsultHomeTrueForCache();

}
