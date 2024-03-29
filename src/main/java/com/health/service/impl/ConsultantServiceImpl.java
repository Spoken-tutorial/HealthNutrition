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

/**
 * Default implementation of the {@link com.health.service.ConsultantService}
 * interface.
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class ConsultantServiceImpl implements ConsultantService {
    private static final Logger logger = LoggerFactory.getLogger(ConsultantServiceImpl.class);

    @Autowired
    private ConsultantRepository consultantRepo;

    /**
     * @see com.health.service.ConsultantService#findAll()
     */
    @Override
    public List<Consultant> findAll() {

        List<Consultant> local = consultantRepo.findAll();

        return local;

    }

    /**
     * @see com.health.service.ConsultantService#deleteProduct(Integer)
     */
    @Override
    public void deleteProduct(Integer id) {

        consultantRepo.deleteById(id);

    }

    /**
     * @see com.health.service.ConsultantService#findById(int)
     */
    @Override
    public Consultant findById(int id) {
        // TODO Auto-generated method stub
        try {
            Optional<Consultant> local = consultantRepo.findById(id);
            return local.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Id error in Consultant Service Impl: {}", id, e);
            return null;
        }
    }

    /**
     * @see com.health.service.ConsultantService#save(Consultant)
     */
    @Override
    public void save(Consultant consult) {
        // TODO Auto-generated method stub

        consultantRepo.save(consult);
    }

    /**
     * @see com.health.service.ConsultantService#getNewConsultantId()
     */
    @Override
    public int getNewConsultantId() {
        // TODO Auto-generated method stub
        try {
            return consultantRepo.getNewId() + 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("New Id error in Consultant Service Impl: {}", consultantRepo.getNewId(), e);
            return 1;
        }
    }

    /**
     * @see com.health.service.ConsultantService#findByUser(User)
     */
    @Override
    public Consultant findByUser(User usr) {
        // TODO Auto-generated method stub
        return consultantRepo.findByuser(usr);
    }

    /**
     * @see com.health.service.ConsultantService#findByOnHome(boolean)
     */
    @Override
    public List<Consultant> findByOnHome(boolean value) {
        // TODO Auto-generated method stub
        return consultantRepo.findByonHome(value);
    }

    @Override
    @Cacheable(cacheNames = "consultants")
    public List<Consultant> findAllConsultHomeTrueForCache() {

        return consultantRepo.findByonHome(true);

    }

}
