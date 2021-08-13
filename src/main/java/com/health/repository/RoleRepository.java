package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.domain.security.Role;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Role object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {
		
	/**
	 * Find Role object given Role name
	 * @param name String object
	 * @return role object
	 */
	Role findByname(String name);

	/**
	 * Find role object given role id
	 * @param id int value
	 * @return role object
	 */
	@Query("from Role u where u.roleId=?1")
	Role findByIdRoles(int id);
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(roleId) from Role")
	long getNewId();
	 

}
