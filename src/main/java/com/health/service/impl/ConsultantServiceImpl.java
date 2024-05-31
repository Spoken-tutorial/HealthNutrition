package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.health.model.Consultant;
import com.health.model.User;
import com.health.repository.ConsultantRepository;
import com.health.service.ConsultantService;

@Service
public class ConsultantServiceImpl implements ConsultantService {
    private static final Logger logger = LoggerFactory.getLogger(ConsultantServiceImpl.class);

    @Autowired
    private ConsultantRepository consultantRepo;

    @Override
    public List<Consultant> findAll() {

        List<Consultant> local = consultantRepo.findAll();

        return local;

    }

    @Override
    public void deleteProduct(Integer id) {

        consultantRepo.deleteById(id);

    }

    @Override
    public Consultant findById(int id) {

        try {
            Optional<Consultant> local = consultantRepo.findById(id);
            return local.get();
        } catch (Exception e) {

            logger.error("Id error in Consultant Service Impl: {}", id, e);
            return null;
        }
    }

    @Override
    public void save(Consultant consult) {

        consultantRepo.save(consult);
    }

    @Override
    public int getNewConsultantId() {

        try {
            return consultantRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in Consultant Service Impl: {}", consultantRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public Consultant findByUser(User usr) {

        return consultantRepo.findByuser(usr);
    }

    @Override
    public List<Consultant> findByOnHome(boolean value) {

        return consultantRepo.findByonHome(value);
    }

    @Override
    @Cacheable(cacheNames = "consultants")
    public List<Consultant> findAllConsultHomeTrueForCache() {

        return consultantRepo.findByonHome(true);

    }

}
