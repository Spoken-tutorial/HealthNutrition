package com.health.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com .health.model.Tutorial;

public class TutorialSpecification implements Specification<Tutorial> {

	 private final SearchCriteria searchCriteria;

	    public TutorialSpecification(final SearchCriteria searchCriteria){
	        super();
	        this.searchCriteria = searchCriteria;
	    }

	    @Override
	    public Predicate toPredicate(Root<Tutorial> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

	        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

	        
	               
	                   
	                
	                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");

	          
	        
	
	    }

	   
	}