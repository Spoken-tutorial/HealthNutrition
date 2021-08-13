package com.health.service;

import java.util.List;

import com.health.model.OrganizationRole;
/**
 * This interface has all the method declaration related to OrganizationRole object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface OrganizationRoleService {

	/**
	 * Find the next unique id for OrganizationRole object
	 * @return primitive integer value
	 */
	int getnewRoleId();

	List<OrganizationRole> findAll();

	/**
	 * Persist OrganizationRole object into database
	 * @param role OrganizationRole object
	 */
	  void save(OrganizationRole role);

	  /**
	   * Find OrganizationRole object given role name
	   * @param role String object
	   * @return OrganizationRole object
	   */
	  OrganizationRole getByRole(String role);

	  /**
	   * Find OrganizationRole object given rold id
	   * @param roleId int value
	   * @return OrganizationRole object
	   */
	  OrganizationRole getById(int roleId);
}
