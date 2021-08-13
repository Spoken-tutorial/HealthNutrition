package com.health.service;

import java.util.List;

import com.health.domain.security.Role;

/**
 * This interface has all the method declaration related to Role object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface RoleService {
	

	/**
	 * Find all the Role object from database
	 * @return
	 */
	  List<Role> findAll();
	  
	  /**
		 * Persist Role object into database
		 * @param role Role object
		 */
	  void save(Role role);
		
	  /**
	   * Find Role object give role name
	   * @param roleName String object
	   * @return Role oject
	   */
	  Role findByname(String roleName);
	  
	  /**
		 * Find the next unique id for brochure object
		 * @return primitive integer value
		 */
	  int getNewRoleId();
	  
	  /**
	   * Find Role object give id
	   * @param id int 
	   * @return Role object
	   */
	  Role findById(int id);


}
