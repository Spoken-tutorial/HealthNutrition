package com.health.search1;

public class SpecSearchCriteria1 {

    private String key;
    private SearchOperation1 operation;
    private Object value;
    private boolean orPredicate;

    public SpecSearchCriteria1() {

    }

    public SpecSearchCriteria1(final String key, final SearchOperation1 operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria1(final String orPredicate, final String key, final SearchOperation1 operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation1.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria1(String key, String operation, String prefix, String value, String suffix) {
        SearchOperation1 op = SearchOperation1.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation1.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation1.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation1.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation1.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation1.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation1.STARTS_WITH;
                }
            }
        }
        this.key = key;
        this.operation = op;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public SearchOperation1 getOperation() {
        return operation;
    }

    public void setOperation(final SearchOperation1 operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public boolean isOrPredicate() {
        return orPredicate;
    }

    public void setOrPredicate(boolean orPredicate) {
        this.orPredicate = orPredicate;
    }

}