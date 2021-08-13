package com.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.domain.security.Role;
import com.health.repository.RoleRepository;
import com.health.service.RoleService;

/**
 * Default implementation of the {@link com.health.service.RoleService} interface.  
 * @author om prakash soni
 * @version 1.0
 */

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * @see com.health.service.RoleService#findAll()
	 */
	@Override
	public List<Role> findAll() {

		List<Role> local = (List<Role>) roleRepository.findAll();
		
		return local;

	}

	/**
	 * @see com.health.service.RoleService#save(Role)
	 */
	@Override
	public void save(Role role) {
		// TODO Auto-generated method stub
		
		roleRepository.save(role);
	}

	/**
	 * @see com.health.service.RoleService#findByname(String)
	 */
	@Override
	public Role findByname(String roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByname(roleName);
	}

	/**
	 * @see com.health.service.RoleService#getNewRoleId()
	 */
	@Override
	public int getNewRoleId() {
		// TODO Auto-generated method stub
		try {
			return (int) (roleRepository.getNewId()+1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.RoleService#findById(int)
	 */
	@Override
	public Role findById(int id) {
		// TODO Auto-generated method stub
		return roleRepository.findById(id).get();
	}

	 

	
	
	
	

	
	
	
	
	

}
