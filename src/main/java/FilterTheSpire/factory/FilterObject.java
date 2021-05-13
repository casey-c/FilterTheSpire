package FilterTheSpire.factory;

import FilterTheSpire.utils.FilterType;

import java.util.List;

public class FilterObject {
    protected FilterType filterType;
    protected List<String> anyOf;
    // Do we need an exclusion list? Seems like we only need the anyOf list
    protected List<String> noneOf;
    protected Integer actNumber; // Integer so it's a nullable reference type for serialization
    protected Integer nthInsideAct;

    public FilterObject(FilterType filterType, List<String> anyOf) {
        this.filterType = filterType;
        this.anyOf = anyOf;
    }
}
