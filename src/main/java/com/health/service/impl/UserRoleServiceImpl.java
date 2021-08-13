package com.health.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Language;
import com.health.model.User;
import com.health.repository.UserRoleRepositary;
import com.health.service.UserRoleService;

/**
 * Default implementation of the {@link com.health.service.UserRoleService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleRepositary usrRoleRepo;
	
	/**
	 * @see com.health.service.UserRoleService#save(UserRole)
	 */
	@Override
	public void save(UserRole usrRole) {
		// TODO Auto-generated method stub
		usrRoleRepo.save(usrRole);
	}

	/**
	 * @see com.health.service.UserRoleService#findAllByRole(Role)
	 */
	@Override
	public List<UserRole> findAllByUser(User usr) {
		// TODO Auto-generated method stub
		return usrRoleRepo.findAllByuser(usr);
	}

	/**
	 * @see com.health.service.UserRoleService#getNewUsrRoletId()
	 */
	@Override
	public long getNewUsrRoletId() {
		// TODO Auto-generated method stub
		try {
			return usrRoleRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.UserRoleService#findByLanCatUser(Language, Category, User, Role)
	 */
	@Override
	public UserRole findByLanCatUser(Language lan, Category cat, User usr,Role role) {
		// TODO Auto-generated method stub
		return usrRoleRepo.findByLanCatUser(lan, cat, usr,role);
	}

	/**
	 * @see com.health.service.UserRoleService#findByLanUser(Language, User, Role)
	 */
	@Override
	public List<UserRole> findByLanUser(Language lan, User usr,Role role) {
		// TODO Auto-generated method stub
		return usrRoleRepo.findByLanUser(lan, usr,role);
	}

	/**
	 * @see com.health.service.UserRoleService#findByRoleUser(User, Role)
	 */
	@Override
	public List<UserRole> findByRoleUser(User usr, Role role) {
		// TODO Auto-generated method stub
		return usrRoleRepo.findByRoleUser(usr, role);
	}

	/**
	 * @see com.health.service.UserRoleService#findAllByRoleAndStatusAndRevoked(Role, boolean, boolean)
	 */
	@Override
	public List<UserRole> findAllByRoleAndStatusAndRevoked(Role role,boolean status,boolean revokeStatus) {
		// TODO Auto-generated method stub
		List<UserRole> data=new ArrayList<UserRole>();
		
		List<UserRole> temp=usrRoleRepo.findAllByrole(role);
		for(UserRole local:temp) {
			if(local.getStatus()== status && local.isRevoked() == revokeStatus && local.isRejected() == false) {
				data.add(local);
			}
		}
		
		return data;
	}

	/**
	 * @see com.health.service.UserRoleService#enableRole(UserRole)
	 */
	@Transactional
	@Override
	public int enableRole(UserRole usrRole) {
		// TODO Auto-generated method stub
		return usrRoleRepo.enableRole(true, usrRole.getUserRoleId());
	}

	/**
	 * @see com.health.service.UserRoleService#findById(long)
	 */
	@Override
	public UserRole findById(long id) {
		// TODO Auto-generated method stub
		Optional<UserRole> local=usrRoleRepo.findById(id);
		return local.get();
	}

	/**
	 * @see com.health.service.UserRoleService#findAllByRole(Role)
	 */
	@Override
	public List<UserRole> findAllByRole(Role role) {
		// TODO Auto-generated method stub
		return usrRoleRepo.findAllByrole(role);
	}

	/**
	 * @see com.health.service.UserRoleService#findAllByRoleUserStatus(Role, User, boolean)
	 */
	@Override
	public List<UserRole> findAllByRoleUserStatus(Role role, User usr, boolean status) {
		// TODO Auto-generated method stub
		return usrRoleRepo.findByRoleUserStatus(usr, role, status);
	}

	/**
	 * @see com.health.service.UserRoleService#delete(UserRole)
	 */
	@Override
	public void delete(UserRole u) {
		// TODO Auto-generated method stub
		usrRoleRepo.deleteById(u.getUserRoleId());
	}

}
