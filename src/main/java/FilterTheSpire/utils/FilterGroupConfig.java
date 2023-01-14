package FilterTheSpire.utils;

import FilterTheSpire.factory.FilterObject;

import java.util.HashMap;
import java.util.List;

public class FilterGroupConfig {
    public HashMap<String, FilterObject> activeFilters;
    private List<PresetFilterGroup> presetFilterGroups;

    public FilterGroupConfig(HashMap<String, FilterObject> activeFilters, List<PresetFilterGroup> presetFilterGroups) {
        this.activeFilters = activeFilters;
        this.presetFilterGroups = presetFilterGroups;
    }
}

