package com.health.service;

import java.util.List;

import com.health.model.OrganizationRole;

public interface OrganizationRoleService {

    int getnewRoleId();

    List<OrganizationRole> findAll();

    void save(OrganizationRole role);

    OrganizationRole getByRole(String role);

    OrganizationRole getById(int roleId);
}
