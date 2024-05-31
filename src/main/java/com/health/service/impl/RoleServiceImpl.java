package com.health.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.domain.security.Role;
import com.health.repository.RoleRepository;
import com.health.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {

        List<Role> local = (List<Role>) roleRepository.findAll();

        return local;

    }

    @Override
    public void save(Role role) {

        roleRepository.save(role);
    }

    @Override
    public Role findByname(String roleName) {

        return roleRepository.findByname(roleName);
    }

    @Override
    public int getNewRoleId() {

        try {
            return (int) (roleRepository.getNewId() + 1);
        } catch (Exception e) {

            logger.error(" New Id error in Role Service Impl: {}", roleRepository.getNewId(), e);
            return 1;
        }
    }

    @Override
    public Role findById(int id) {

        return roleRepository.findById(id).get();
    }

}
