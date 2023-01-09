package com.health.search;



public class SearchCriteria {

    private String filterKey;
    private String value;
   
    public SearchCriteria(String filterKey,  String value){
        super();
        this.filterKey = filterKey;
        
        this.value = value;
    }

	public SearchCriteria() {
		super();
		
	}

	public String getFilterKey() {
		return filterKey;
	}

	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
    
}