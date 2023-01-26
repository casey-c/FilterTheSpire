package FilterTheSpire.utils.config;

import FilterTheSpire.factory.FilterObject;

import java.util.HashMap;
import java.util.List;

public class FilterGroupConfig {
    public HashMap<FilterType, FilterObject> activeFilters;
    private List<PresetFilterGroup> presetFilterGroups;

    public FilterGroupConfig(HashMap<FilterType, FilterObject> activeFilters, List<PresetFilterGroup> presetFilterGroups) {
        this.activeFilters = activeFilters;
        this.presetFilterGroups = presetFilterGroups;
    }
}

