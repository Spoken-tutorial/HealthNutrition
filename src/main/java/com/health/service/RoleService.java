package com.health.service;

import java.util.List;

import com.health.domain.security.Role;

public interface RoleService {

    List<Role> findAll();

    void save(Role role);

    Role findByname(String roleName);

    int getNewRoleId();

    Role findById(int id);

}
