package com.health.search;


import java.util.ArrayList;
import java.util.List;


public class TutorialSearchDto {

    private List<SearchCriteria> searchCriteriaList;
  
    
    
	public TutorialSearchDto(String filterKey, String query) {
		searchCriteriaList=new ArrayList<>();
		for(String s: query.split(" ")) {
			searchCriteriaList.add(new SearchCriteria(filterKey, s));
		}
		
	}
	
	public TutorialSearchDto() {
		
	}



	public List<SearchCriteria> getSearchCriteriaList() {
		return searchCriteriaList;
	}


	

	
    
	

}