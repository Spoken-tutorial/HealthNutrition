package com.health.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("select max(id) from User")
    long getNewId();

    User findBytoken(String token);

    @Query("UPDATE User c SET c.enabled = true WHERE c.id = ?1")
    @Modifying
    public static void enable(Long Id) {

    }

    @Query("SELECT c FROM User c WHERE c.emailVerificationCode= ?1")
    public static User findByVerificationCode(String code) {

        return null;
    }

}
