package com.health.service;

import java.util.List;

import com.health.model.Consultant;
import com.health.model.User;


/**
 * This interface has all the method declaration related to Consultant object database operation
 * @author om prakash soni
 * @version 1.0
 *
 */
public interface ConsultantService {
	
	/**
	 * Find all the Consultant object from database
	 * @return list of Consultant object
	 */
	List<Consultant> findAll();     // in use
	
	/**
	 * Delete Consultant object from database given id
	 * @param id int value
	 */
    void deleteProduct(Integer id);     
    
    /**
     * Find Consultant object given it
     * @param id int value
     * @return Consultant object
     */
    Consultant findById(int id);    // in use

    /**
	 * Persist Consultant object into database
	 * @param consult Consultant object
	 */
	void save(Consultant consult);   // in use
	
	/**
	 * Find the next unique id for the object
	 * @return primitive integer value
	 */
	int getNewConsultantId();       // in use
	
	/**
	 * find Consultant object given user object
	 * @param usr user object
	 * @return Consultant object
	 */
	Consultant findByUser(User usr);
	
	/**
	 * find list of Consultant object given onHome field
	 * @param value boolean 
	 * @return list of Consultant object
	 */
	List<Consultant> findByOnHome(boolean value);
	

}
