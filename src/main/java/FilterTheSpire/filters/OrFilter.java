package FilterTheSpire.filters;

import java.util.ArrayList;

// Take a collection of filters and return true if any one is valid.
public class OrFilter extends AbstractFilter {
    private ArrayList<AbstractFilter> filters;

    public OrFilter(ArrayList<AbstractFilter> filters) {
        this.filters = filters;
    }

    public boolean isSeedValid(long seed) {
        for (AbstractFilter filter : filters) {
            if (filter.isSeedValid(seed)) {
                return true;
            }
        }
        return false;
    }
}
