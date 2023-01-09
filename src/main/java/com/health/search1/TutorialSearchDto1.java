package com.health.search1;



import java.util.List;

public class TutorialSearchDto1 {

    private List<SearchCriteria1> searchCriteriaList;
    private String dataOption;
	
    public TutorialSearchDto1(List<SearchCriteria1> searchCriteriaList, String dataOption) {
		super();
		this.searchCriteriaList = searchCriteriaList;
		this.dataOption = dataOption;
	}

	public TutorialSearchDto1() {
		super();
	}

	public List<SearchCriteria1> getSearchCriteriaList() {
		return searchCriteriaList;
	}

	public void setSearchCriteriaList(List<SearchCriteria1> searchCriteriaList) {
		this.searchCriteriaList = searchCriteriaList;
	}

	public String getDataOption() {
		return dataOption;
	}

	public void setDataOption(String dataOption) {
		this.dataOption = dataOption;
	}
    
    

}