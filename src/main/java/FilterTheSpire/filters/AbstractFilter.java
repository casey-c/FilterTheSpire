package FilterTheSpire.filters;

import FilterTheSpire.utils.config.FilterType;

public abstract class AbstractFilter {
    public FilterType type;
    protected int sortOrder = 0;
    public int getSortOrder() {
        return sortOrder;
    }
    public abstract boolean isSeedValid(long seed);
}
