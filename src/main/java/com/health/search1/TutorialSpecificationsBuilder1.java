package com.health.search1;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;


public final class TutorialSpecificationsBuilder1 {

	 private final List<SpecSearchCriteria1> params;

	    public TutorialSpecificationsBuilder1() {
	        params = new ArrayList<>();
	    }

	    public final TutorialSpecificationsBuilder1 with(String key, String operation, Object value, 
	      String prefix, String suffix) {
	        return with(null, key, operation, value, prefix, suffix);
	    }

	    public final TutorialSpecificationsBuilder1 with(String orPredicate, String key, String operation, 
	      Object value, String prefix, String suffix) {
	        SearchOperation1 op = SearchOperation1.getSimpleOperation(operation.charAt(0));
	        if (op != null) {
	            if (op == SearchOperation1.EQUALITY) { // the operation may be complex operation
	                boolean startWithAsterisk = prefix != null && 
	                  prefix.contains(SearchOperation1.ZERO_OR_MORE_REGEX);
	                boolean endWithAsterisk = suffix != null && 
	                  suffix.contains(SearchOperation1.ZERO_OR_MORE_REGEX);

	                if (startWithAsterisk && endWithAsterisk) {
	                    op = SearchOperation1.CONTAINS;
	                } else if (startWithAsterisk) {
	                    op = SearchOperation1.ENDS_WITH;
	                } else if (endWithAsterisk) {
	                    op = SearchOperation1.STARTS_WITH;
	                }
	            }
	            params.add(new SpecSearchCriteria1(orPredicate, key, op, value));
	        }
	        return this;
	    }

	    public Specification build() {
	        if (params.size() == 0)
	            return null;

	        Specification result = new TutorialSpecification1(params.get(0));
	     
	        for (int i = 1; i < params.size(); i++) {
	            result = params.get(i).isOrPredicate()
	              ? Specification.where(result).or(new TutorialSpecification1(params.get(i))) 
	              : Specification.where(result).and(new TutorialSpecification1(params.get(i)));
	        }
	        
	        return result;
	    }
	}