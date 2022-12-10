package FilterTheSpire.factory;

import FilterTheSpire.utils.FilterType;

import java.util.*;

public class FilterObject {
    public FilterType filterType;
    public List<String> anyOf;
    // Do we need an exclusion list? Seems like we only need the anyOf list
    public List<String> noneOf;
    protected Integer actNumber; // Integer so it's a nullable reference type for serialization
    public List<Integer> possibleEncounterIndices;

    public FilterObject(FilterType filterType) {
        this.filterType = filterType;
        this.anyOf = new ArrayList<>();
        this.noneOf = new ArrayList<>();
        this.possibleEncounterIndices = Collections.singletonList(0);
    }

    public FilterObject(FilterType filterType, List<String> anyOf) {
        this.filterType = filterType;
        this.anyOf = anyOf;
        this.possibleEncounterIndices = Collections.singletonList(0);
    }
}
