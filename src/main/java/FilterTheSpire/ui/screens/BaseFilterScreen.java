package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.utils.config.FilterType;
import basemod.ModLabeledButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

public abstract class BaseFilterScreen extends BaseScreen {
    public FilterObject filterObject;
    public boolean hasAddButton = false;
    public final ModLabeledButton addButton;
    public final FilterType filterType;

    public BaseFilterScreen( FilterType filterType, ModPanel p) {
        this.filterType = filterType;
        addButton = new ModLabeledButton("Add",
                BaseFilterScreen.INFO_LEFT + 170.0F, 805.0f,
                Settings.CREAM_COLOR, Color.GREEN, FontHelper.tipHeaderFont, p,
                (self) -> {
                    refreshFilters();
                    resetUI();
                });
    }

    public void clearFilter(){
        filterObject = new FilterObject(filterType);
    }

    public void refreshFilters() {
        FilterTheSpire.config.addOrUpdateFilter(filterObject);
        FilterManager.setFilter(filterObject);
    }

    abstract void resetUI();
}

