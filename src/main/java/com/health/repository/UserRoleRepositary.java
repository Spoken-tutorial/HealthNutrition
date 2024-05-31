package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.Language;
import com.health.model.User;

public interface UserRoleRepositary extends CrudRepository<UserRole, Long> {

    @Query("select max(userRoleId) from UserRole")
    long getNewId();

    @Query("from UserRole where user=?1 order by role, cat, lan ")
    List<UserRole> findAllByuser(User usr);

    @Query("from UserRole where lan=?1 and cat=?2 and user=?3 and role=?4")
    UserRole findByLanCatUser(Language lan, Category cat, User usr, Role role);

    @Query("from UserRole where lan=?1 and user=?2 and role=?3")
    List<UserRole> findByLanUser(Language lan, User usr, Role role);

    @Query("from UserRole where user=?1 and role=?2")
    List<UserRole> findByRoleUser(User usr, Role role);

    @Query("from UserRole where user=?1 and role=?2 and status=?3")
    List<UserRole> findByRoleUserStatus(User usr, Role role, boolean status);

    List<UserRole> findAllByrole(Role role);

    @Modifying
    @Query("update UserRole set status=?1 where userRoleId=?2")
    int enableRole(boolean status, long usrRoleId);

}
