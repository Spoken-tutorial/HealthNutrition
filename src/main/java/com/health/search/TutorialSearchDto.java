package com.health.search;


import java.util.List;


public class TutorialSearchDto {

    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;
    
    
	public TutorialSearchDto() {
		super();
	}


	public TutorialSearchDto(List<SearchCriteria> searchCriteriaList, String dataOption) {
		super();
		this.searchCriteriaList = searchCriteriaList;
		this.dataOption = dataOption;
	}


	public List<SearchCriteria> getSearchCriteriaList() {
		return searchCriteriaList;
	}


	public void setSearchCriteriaList(List<SearchCriteria> searchCriteriaList) {
		this.searchCriteriaList = searchCriteriaList;
	}


	public String getDataOption() {
		return dataOption;
	}


	public void setDataOption(String dataOption) {
		this.dataOption = dataOption;
	}
    
	

}