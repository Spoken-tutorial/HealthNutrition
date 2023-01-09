package com.health.search;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.health.model.Tutorial;

public class TutorialSpecificationBuilder {

    private final List<SearchCriteria> params;

    public TutorialSpecificationBuilder(){
        this.params = new ArrayList<>();
    }

    public final TutorialSpecificationBuilder with(String key, String value){
        params.add(new SearchCriteria(key, value));
        return this;
    }

    public final TutorialSpecificationBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<Tutorial> build(){
        if(params.size() == 0){
            return null;
        }

        Specification<Tutorial> result = new TutorialSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++){
            SearchCriteria criteria = params.get(idx);
            result =  Specification.where(result).and(new TutorialSpecification(criteria));
                    
        }

        return result;
    }
}