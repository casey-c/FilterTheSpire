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
import java.util.List;

public class FilterListScreen extends BaseFilterScreen {
    private final ArrayList<AbstractFilter> filterList;

    public boolean isShowing = false;

    public FilterListScreen(){
        List<FilterObject> filters = FilterTheSpire.config.getActiveFilters();
        filterList = new ArrayList<>();
        for (FilterObject filter: filters) {
            filterList.add(FilterFactory.getAbstractFilterFromFilterObject(filter));
        }
    }

    void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        this.returnButton.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Filter List", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        for (int i = 0; i < filterList.size(); i++) {
            AbstractFilter filter = filterList.get(i);
            FontHelper.renderSmartText(sb,
                    FontHelper.tipBodyFont,
                    filter.toString(),
                    400.0F * Settings.xScale,
                    (530.0F + (30.0F * i)) * Settings.yScale,
                    1000.0F * Settings.xScale,
                    30.0f * Settings.yScale,
                    Settings.CREAM_COLOR);
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
