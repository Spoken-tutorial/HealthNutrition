package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.OrganizationRole;

public interface OrganizationRoleRepository extends CrudRepository<OrganizationRole, Integer> {

    @Query("from OrganizationRole where role = ?1")
    OrganizationRole findByRole(String role);

    @Query("select max(roleId) from OrganizationRole")
    int getNewId();

}
