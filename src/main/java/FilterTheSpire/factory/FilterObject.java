package FilterTheSpire.factory;

import FilterTheSpire.utils.FilterType;

import java.util.ArrayList;
import java.util.List;

public class FilterObject {
    public FilterType filterType;
    public List<String> anyOf;
    // Do we need an exclusion list? Seems like we only need the anyOf list
    protected List<String> noneOf;
    protected Integer actNumber; // Integer so it's a nullable reference type for serialization
    public Integer encounterIndex;

    public FilterObject(FilterType filterType) {
        this.filterType = filterType;
        this.anyOf = new ArrayList<>();
        this.encounterIndex = 0;
    }

    public FilterObject(FilterType filterType, List<String> anyOf) {
        this.filterType = filterType;
        this.anyOf = anyOf;
        this.encounterIndex = 0;
    }
}
