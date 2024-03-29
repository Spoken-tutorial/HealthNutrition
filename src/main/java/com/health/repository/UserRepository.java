package com.health.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related
 * to User object
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find User object given username
     * 
     * @param username String object
     * @return user object
     */
    User findByUsername(String username);

    /**
     * Find User object given email
     * 
     * @param email String object
     * @return user object
     */
    User findByEmail(String email);

    /**
     * Find the next unique id for the object
     * 
     * @return primitive integer value
     */
    @Query("select max(id) from User")
    long getNewId();

    /**
     * Find User object given Token
     * 
     * @param token String object
     * @return user object
     */
    User findBytoken(String token);

    @Query("UPDATE User c SET c.enabled = true WHERE c.id = ?1")
    @Modifying
    public static void enable(Long Id) {
        // TODO Auto-generated method stub

    }

    @Query("SELECT c FROM User c WHERE c.emailVerificationCode= ?1")
    public static User findByVerificationCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }

//	String getEmailVerificationCode();

//	String setEmailVerificationCode();

}
