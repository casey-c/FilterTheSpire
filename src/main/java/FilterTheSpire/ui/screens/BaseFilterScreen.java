package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.utils.config.FilterType;
import basemod.ModLabeledButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

public abstract class BaseFilterScreen extends BaseScreen {
    public FilterObject filterObject;
    public boolean hasAddButton;
    public final ModLabeledButton addButton;
    public final FilterType filterType;

    public BaseFilterScreen(FilterType filterType, ModPanel p, boolean hasAddButton) {
        this.hasAddButton = hasAddButton;
        this.filterType = filterType;
        addButton = new ModLabeledButton("Add",
                BaseFilterScreen.INFO_LEFT + 190.0F, 805.0f,
                Settings.CREAM_COLOR, Color.GREEN, FontHelper.tipHeaderFont, p,
                (self) -> {
                    updateFilters();
                    resetUI();
                });
    }

    public void clearFilter(){
        filterObject = new FilterObject(filterType);
    }

    public void updateFilters() {
        setFilterObjectForAddOrUpdate();
        FilterTheSpire.config.addOrUpdateFilter(filterObject);
        if (hasAddButton){
            FilterManager.addFilter(filterObject);
        } else {
            FilterManager.setFilter(filterObject);
        }
    }

    public void render(SpriteBatch sb){
        super.render(sb);
        sb.setColor(Color.WHITE);
        if (hasAddButton){
            this.addButton.render(sb);
        }
        this.returnButton.render(sb);
    }

    public void setFilterObjectForAddOrUpdate(){

    }

    abstract void resetUI();
}

