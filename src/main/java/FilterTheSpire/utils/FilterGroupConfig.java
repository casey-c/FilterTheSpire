package FilterTheSpire.utils;

import FilterTheSpire.factory.FilterObject;

import java.util.List;

public class FilterGroupConfig {
    private List<FilterObject> activeFilters;
    private List<PresetFilterGroup> presetFilterGroups;

    public FilterGroupConfig(List<FilterObject> activeFilters, List<PresetFilterGroup> presetFilterGroups) {
        this.activeFilters = activeFilters;
        this.presetFilterGroups = presetFilterGroups;
    }
}

