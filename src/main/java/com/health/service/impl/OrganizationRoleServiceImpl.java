package com.health.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.OrganizationRole;
import com.health.repository.OrganizationRoleRepository;
import com.health.service.OrganizationRoleService;

/**
 * Default implementation of the {@link com.health.service.OrganizationRoleService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class OrganizationRoleServiceImpl implements OrganizationRoleService {

	@Autowired
	OrganizationRoleRepository repo;
	
	/**
	 * @see com.health.service.OrganizationRoleService#findAll()
	 */
	@Override
	public List<OrganizationRole> findAll() {

		List<OrganizationRole> local= (List<OrganizationRole>) (repo.findAll());
		Collections.sort(local);
		return local;
	}
	
	/**
	 * @see com.health.service.OrganizationRoleService#save(OrganizationRole)
	 */
	@Override
	public void save(OrganizationRole role) {

		repo.save(role);
	}
	
	/**
	 * @see com.health.service.OrganizationRoleService#getByRole(String)
	 */
	@Override
	public OrganizationRole getByRole(String role) {

		return (OrganizationRole) repo.findByRole(role);
	}
	
	/**
	 * @see com.health.service.OrganizationRoleService#getnewRoleId()
	 */
	@Override
	public int getnewRoleId() {
		try {
			return repo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}

	}

	/**
	 * @see com.health.service.OrganizationRoleService#getById(int)
	 */
	@Override
	public OrganizationRole getById(int roleId) {

		try {
			Optional<OrganizationRole> local=repo.findById(roleId);
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
