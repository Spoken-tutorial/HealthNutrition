package com.health.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Language;
import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to UserRole object
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface UserRoleRepositary extends  CrudRepository<UserRole, Long>{
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	@Query("select max(userRoleId) from UserRole")
	long getNewId();
	
	/**
	 * Find List of UserRole object given user object
	 * @param usr user object
	 * @return List of UserRole object
	 */
	List<UserRole> findAllByuser(User usr);
	
	/**
	 * Find UserRole Object given Language, CAtegory,User and role object
	 * @param lan Language object
	 * @param cat Category object
	 * @param usr user object
	 * @param role role object
	 * @return UserRole object
	 */
	@Query("from UserRole where lan=?1 and cat=?2 and user=?3 and role=?4")
	UserRole findByLanCatUser(Language lan,Category cat,User usr,Role role);
	
	/**
	 * List of UserRole object given user , role and language object
	 * @param lan language object
	 * @param usr user object
	 * @param role role object
	 * @return List of UserRole object
	 */
	@Query("from UserRole where lan=?1 and user=?2 and role=?3")
	List<UserRole> findByLanUser(Language lan,User usr,Role role);
	
	/**
	 * List of UserRole object given user and role object
	 * @param usr user object
	 * @param role role object
	 * @return List of UserRole object
	 */
	@Query("from UserRole where user=?1 and role=?2")
	List<UserRole> findByRoleUser(User usr,Role role);
	
	/**
	 * List of UserRole object given user, role and status value
	 * @param usr user object
	 * @param role role object
	 * @param status boolean value
	 * @return List of UserRole object
	 */
	@Query("from UserRole where user=?1 and role=?2 and status=?3")
	List<UserRole> findByRoleUserStatus(User usr,Role role,boolean status);
	
	/**
	 * Find List of UserRole object given role object
	 * @param role role object
	 * @return List of UserRole object
	 */
	List<UserRole> findAllByrole(Role role);
	
	/**
	 * update UserRole object 
	 * @param status boolean value
	 * @param usrRoleId int value
	 * @return int value
	 */
	@Modifying
	@Query("update UserRole set status=?1 where userRoleId=?2")
	int enableRole(boolean status, long usrRoleId);
	
			
}
