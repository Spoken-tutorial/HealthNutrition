package com.health.service;

import java.util.List;

import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Language;
import com.health.model.User;

/**
 * This interface has all the method declaration related to UserRole object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface UserRoleService {

	/**
	 * Persist UserRole object into database
	 * @param usrRole UserRole object
	 */
	void save(UserRole usrRole);
	
	/**
	 * List of UserRole object given user object
	 * @param usr user object
	 * @return List of UserRole object
	 */
	List<UserRole> findAllByUser(User usr);
	
	/**
	 * Find the next unique id for UserRole object
	 * @return primitive integer value
	 */
	long getNewUsrRoletId();
	
	/**
	 * Find UserRole Object given Language, CAtegory,User and role object
	 * @param lan Language object
	 * @param cat Category object
	 * @param usr user object
	 * @param role role object
	 * @return UserRole object
	 */
	UserRole findByLanCatUser(Language lan,Category cat,User usr,Role role);
	
	/**
	 * List of UserRole object given user , role and language object
	 * @param lan language object
	 * @param usr user object
	 * @param role role object
	 * @return List of UserRole object
	 */
	List<UserRole> findByLanUser(Language lan,User usr,Role role);
	
	/**
	 * List of UserRole object given user and role object
	 * @param usr user object
	 * @param role role object
	 * @return List of UserRole object
	 */
	List<UserRole> findByRoleUser(User usr,Role role);
	
	/**
	 * Find All the UserRole objects given Role object, status and revoked status value
	 * @param role role object
	 * @param status boolean
	 * @param revokeStatus boolean
	 * @return List of UserRole object
	 */
	List<UserRole> findAllByRoleAndStatusAndRevoked(Role role,boolean status,boolean revokeStatus);
	
	/**
	 * update UserRole Object 
	 * @param usrRole UserRole object
	 * @return int value
	 */
	int enableRole(UserRole usrRole);
	
	/**
	 * Find UserRole Object given Id
	 * @param id long Value
	 * @return UserRole object
	 */
	UserRole findById(long id);
	
	/**
	 * Find List of UserRole object given role object
	 * @param role role object
	 * @return List of UserRole object
	 */
	List<UserRole> findAllByRole(Role role);
	
	/**
	 * List of UserRole object given user, role and status value
	 * @param usr user object
	 * @param role role object
	 * @param status boolean value
	 * @return List of UserRole object
	 */
	List<UserRole> findAllByRoleUserStatus(Role role,User usr, boolean status);
	
	/**
	 * delete userRole object from database
	 * @param u userRole object
	 */
	void delete(UserRole u);
	
}
