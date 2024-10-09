package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.factory.FilterFactory;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.filters.AbstractFilter;
import FilterTheSpire.utils.ExtraFonts;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FilterListScreen extends BaseFilterScreen {
    private ArrayList<AbstractFilter> filterList;

    public FilterListScreen(){
        filterList = new ArrayList<>();
    }

    public void open(){
        List<FilterObject> filters = FilterTheSpire.config.getActiveFilters();
        filterList = new ArrayList<>();
        for (FilterObject filter: filters) {
            filterList.add(FilterFactory.getAbstractFilterFromFilterObject(filter));
        }
        filterList.sort(Comparator.comparing(f -> f.displayName));
    }

    void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        this.returnButton.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Filter List", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        float currentY = 800.0F;
        for (AbstractFilter abstractFilter : filterList) {
            if (abstractFilter.shouldDisplay()){
                currentY -= 60.0F;
                FontHelper.renderSmartText(sb,
                        FontHelper.tipBodyFont,
                        abstractFilter.displayName,
                        400.0F * Settings.xScale,
                        currentY * Settings.yScale,
                        Settings.WIDTH - (400.0F * Settings.xScale * 2),
                        30.0f * Settings.yScale,
                        Settings.GOLD_COLOR);
                currentY -= 30.0F;
                FontHelper.renderSmartText(sb,
                        FontHelper.tipBodyFont,
                        abstractFilter.toString(),
                        420.0F * Settings.xScale,
                        currentY * Settings.yScale,
                        Settings.WIDTH - (420.0F * Settings.xScale * 2),
                        30.0f * Settings.yScale,
                        Settings.CREAM_COLOR);
            }
        }
    }

    void update() {
        this.returnButton.update();

        if (this.returnButton.hb.clickStarted){
            this.isShowing = false;
        }

        if (isShowing){
            this.returnButton.show();
        } else{
            this.returnButton.hide();
        }
    }

    void resetUI() {

    }
}
