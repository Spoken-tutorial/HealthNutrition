package com.health.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.OrganizationRole;
import com.health.repository.OrganizationRoleRepository;
import com.health.service.OrganizationRoleService;

@Service
public class OrganizationRoleServiceImpl implements OrganizationRoleService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRoleServiceImpl.class);

    @Autowired
    OrganizationRoleRepository repo;

    @Override
    public List<OrganizationRole> findAll() {

        List<OrganizationRole> local = (List<OrganizationRole>) (repo.findAll());
        Collections.sort(local);
        return local;
    }

    @Override
    public void save(OrganizationRole role) {

        repo.save(role);
    }

    @Override
    public OrganizationRole getByRole(String role) {

        return repo.findByRole(role);
    }

    @Override
    public int getnewRoleId() {
        try {
            return repo.getNewId() + 1;
        } catch (Exception e) {

            logger.error(" New Id error in  Organization Role Service Impl: {}", repo.getNewId(), e);
            return 1;
        }

    }

    @Override
    public OrganizationRole getById(int roleId) {

        try {
            Optional<OrganizationRole> local = repo.findById(roleId);
            return local.get();
        } catch (Exception e) {

            logger.error("Id error in  Organization Role Service Impl: {}", roleId, e);
            return null;
        }
    }

}
