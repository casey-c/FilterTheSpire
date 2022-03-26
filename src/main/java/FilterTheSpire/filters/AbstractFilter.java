package FilterTheSpire.filters;

public abstract class AbstractFilter {
    protected int sortOrder = 0;
    public int getSortOrder() {
        return sortOrder;
    }
    public abstract boolean isSeedValid(long seed);
}
