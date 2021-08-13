package com.health.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.Category;


import com.health.repository.CategoryRepository;
import com.health.service.CategoryService;

/**
 * Default implementation of the {@link com.health.service.CategoryService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class CatgoryServiceImpl implements CategoryService {

	@Autowired 
	private CategoryRepository  categoryRepo;
	
	/**
	 * @see com.health.service.CategoryService#findAll()
	 */
	@Override
	public List<Category> findAll() {

		List<Category> cats= (List<Category>) categoryRepo.findAll();
		Collections.sort(cats);
		return cats;

	}
	
	/**
	 * @see com.health.service.CategoryService#findBycategoryname(String)
	 */
	@Override
	public Category findBycategoryname(String name) {

		try {
			Category local =  categoryRepo.findBycatName(name);

			return local;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * @see com.health.service.CategoryService#deleteProduct(Integer)
	 */
	@Override
	public void deleteProduct(Integer id){

		categoryRepo.deleteById(id);																																																																									
	}
	
	/**
	 * @see com.health.service.CategoryService#updateCategory(String, int)
	 */ 
	@Override
	@Transactional																																																							
	public Boolean updateCategory(String testimonialName,int id) {
			
//			int status=	categoryRepo.updateCategory(testimonialName,id);
//
//			
//			if(status>0) {
//				return true;
//			}else {
				return false;
			
		
	}
		  
	/**
	 * @see com.health.service.CategoryService#findByid(int)
	 */
	@Override 
	public Category findByid(int id){
	  
		try {
			Optional<Category> var=categoryRepo.findById(id);
			return var.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	  
	 }

	/**
	 * @see com.health.service.CategoryService#getNewCatId()
	 */
	@Override
	public int getNewCatId() {
		
		try {
			return categoryRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1; 
		}
		
	}

	/**
	 * @see com.health.service.CategoryService#save(Category)
	 */
	@Override
	public Category save(Category cat) {
		// TODO Auto-generated method stub
		categoryRepo.save(cat);
		return null;
	}

	  
	
	

}
