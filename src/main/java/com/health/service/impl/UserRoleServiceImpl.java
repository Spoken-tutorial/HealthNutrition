package com.health.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Language;
import com.health.model.User;
import com.health.repository.UserRoleRepositary;
import com.health.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private UserRoleRepositary usrRoleRepo;

    @Override
    public void save(UserRole usrRole) {

        usrRoleRepo.save(usrRole);
    }

    @Override
    public List<UserRole> findAllByUser(User usr) {

        List<UserRole> list1 = usrRoleRepo.findAllByuser(usr);
        Collections.sort(list1, UserRole.RoleNameComp);
        return list1;
    }

    @Override
    public long getNewUsrRoletId() {

        try {
            return usrRoleRepo.getNewId() + 1;
        } catch (Exception e) {

            logger.error("New Id error in User Role Service Impl: {}", usrRoleRepo.getNewId(), e);
            return 1;
        }
    }

    @Override
    public UserRole findByLanCatUser(Language lan, Category cat, User usr, Role role) {

        return usrRoleRepo.findByLanCatUser(lan, cat, usr, role);
    }

    @Override
    public List<UserRole> findByLanUser(Language lan, User usr, Role role) {

        return usrRoleRepo.findByLanUser(lan, usr, role);
    }

    @Override
    public List<UserRole> findByRoleUser(User usr, Role role) {

        return usrRoleRepo.findByRoleUser(usr, role);
    }

    @Override
    public List<UserRole> findAllByRoleAndStatusAndRevoked(Role role, boolean status, boolean revokeStatus) {

        List<UserRole> data = new ArrayList<UserRole>();

        List<UserRole> temp = usrRoleRepo.findAllByrole(role);
        for (UserRole local : temp) {
            if (local.getStatus() == status && local.isRevoked() == revokeStatus && local.isRejected() == false) {
                data.add(local);
            }
        }

        return data;
    }

    @Transactional
    @Override
    public int enableRole(UserRole usrRole) {

        return usrRoleRepo.enableRole(true, usrRole.getUserRoleId());
    }

    @Override
    public UserRole findById(long id) {

        Optional<UserRole> local = usrRoleRepo.findById(id);
        return local.get();
    }

    @Override
    public List<UserRole> findAllByRole(Role role) {

        return usrRoleRepo.findAllByrole(role);
    }

    @Override
    public List<UserRole> findAllByRoleUserStatus(Role role, User usr, boolean status) {

        return usrRoleRepo.findByRoleUserStatus(usr, role, status);
    }

    @Override
    public void delete(UserRole u) {

        usrRoleRepo.deleteById(u.getUserRoleId());
    }

}
