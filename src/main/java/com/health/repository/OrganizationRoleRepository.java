package com.health.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.OrganizationRole;

/**
 * This Interface Extend CrudRepository to handle all database operation related to OrganizationRole object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface OrganizationRoleRepository extends CrudRepository<OrganizationRole, Integer>{

	/**
	 * Find OrganizationRole object give role name
	 * @param role String object
	 * @return OrganizationRole object
	 */
	@Query("from OrganizationRole where role = ?1")
	OrganizationRole findByRole(String role);

	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(roleId) from OrganizationRole")
	int getNewId();

}
