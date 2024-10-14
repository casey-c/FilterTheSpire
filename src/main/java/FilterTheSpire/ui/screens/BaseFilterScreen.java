package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.utils.config.FilterType;

public abstract class BaseFilterScreen extends BaseScreen {
    public FilterObject filterObject;

    public final FilterType filterType;

    public BaseFilterScreen(FilterType filterType) {
        this.filterType = filterType;
    }

    public void clearFilter(){
        filterObject = new FilterObject(filterType);
    }

    public void refreshFilters() {
        FilterTheSpire.config.updateFilter(filterObject);
        FilterManager.setFilter(filterObject);
    }

    abstract void resetUI();
}

