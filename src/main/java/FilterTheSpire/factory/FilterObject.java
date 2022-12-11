package FilterTheSpire.factory;

import FilterTheSpire.utils.FilterType;

import java.util.*;

public class FilterObject {
    public FilterType filterType;
    public List<String> possibleValues;
    // Do we need an exclusion list? Seems like we only need the anyOf list
    public List<String> secondaryValues;
    protected Integer actNumber; // Integer so it's a nullable reference type for serialization
    public List<Integer> possibleEncounterIndices;
    public HashMap<String, Integer> searchCards;

    public FilterObject(FilterType filterType) {
        this.filterType = filterType;
        this.possibleValues = new ArrayList<>();
        this.secondaryValues = new ArrayList<>();
        this.possibleEncounterIndices = Collections.singletonList(0);
        this.searchCards = new HashMap<>();
    }

    public FilterObject(FilterType filterType, List<String> possibleValues) {
        this.filterType = filterType;
        this.possibleValues = possibleValues;
        this.possibleEncounterIndices = Collections.singletonList(0);
    }
}
