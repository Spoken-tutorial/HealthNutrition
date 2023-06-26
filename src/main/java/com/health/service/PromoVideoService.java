package com.health.service;

import java.util.List;


import com.health.model.PromoVideo;


public interface PromoVideoService {
	
int getNewId();

	
	void save( PromoVideo promoVideo);
	
	List<PromoVideo> findAll();
	
	void delete(PromoVideo temp);

	PromoVideo findById(int id);
	
	
	
	
	void  saveAll(List<PromoVideo> promovideos);


	List<PromoVideo> findAllByShowOnHomePage();
	


}
