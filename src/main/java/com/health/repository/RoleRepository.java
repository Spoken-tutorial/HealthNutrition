package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByname(String name);

    @Query("from Role u where u.roleId=?1")
    Role findByIdRoles(int id);

    @Query("select max(roleId) from Role")
    long getNewId();

}
