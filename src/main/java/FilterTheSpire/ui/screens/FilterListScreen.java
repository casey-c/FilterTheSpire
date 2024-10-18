package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.FilterManager;
import FilterTheSpire.factory.FilterFactory;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.filters.AbstractFilter;
import FilterTheSpire.utils.ExtraFonts;
import basemod.ModLabeledButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FilterListScreen extends BaseScreen {
    private ArrayList<FilterListItem> filterList;

    private final float filterTextLineWidth;
    private final float filterTextStartPositionX = 440.0f;

    public FilterListScreen(){
        filterList = new ArrayList<>();
        filterTextLineWidth = Settings.WIDTH - (filterTextStartPositionX * Settings.xScale * 2);
    }

    public void open(){
        initialize();
        isShowing = true;
    }

    private void initialize(){
        List<FilterObject> filters = FilterTheSpire.config.getActiveFilters();
        filterList = new ArrayList<>();
        ArrayList<AbstractFilter> abstractFilters = new ArrayList<>();
        for (FilterObject filter: filters) {
            AbstractFilter abstractFilter = FilterFactory.getAbstractFilterFromFilterObject(filter);
            abstractFilters.add(abstractFilter);
        }
        abstractFilters.sort(Comparator.comparing(f -> f.displayName));
        float currentY = 680.0f;
        for (AbstractFilter abstractFilter : abstractFilters) {
            if (abstractFilter.shouldDisplay()){
                ModLabeledButton deleteButton = new ModLabeledButton("X",
                        360.0f,
                        currentY,
                        Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR,
                        Color.RED,
                        FontHelper.tipBodyFont,
                        null,
                        (self) -> {
                            FilterTheSpire.config.removeFilter(abstractFilter);
                            FilterManager.loadInitialFilters();
                            this.filterList.removeIf((f) -> Objects.equals(f.filter.generateHashKey(), abstractFilter.generateHashKey()));
                            initialize();
                        }
                );
                FilterListItem filterListItem = new FilterListItem(abstractFilter, deleteButton);
                filterList.add(filterListItem);
                currentY -= (Math.max(getFilterStringHeight(filterListItem.filter), 60.0f) + 30.0f);
            }
        }
    }

    private float getFilterStringHeight(AbstractFilter filter){
        float height = FontHelper.getSmartHeight(FontHelper.tipBodyFont,
                filter.toString(),
                filterTextLineWidth,
                30.0f * Settings.yScale);
        return Math.abs(height) + 10.0f;
    }

    void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        this.returnButton.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Filter List", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        float currentY = 800.0F;
        float yDifference = 60.0F;
        for (FilterListItem filterListItem : filterList) {
            if (filterListItem.filter.shouldDisplay()){
                currentY -= Math.max(yDifference, 60.0F);
                FontHelper.renderSmartText(sb,
                        FontHelper.tipBodyFont,
                        filterListItem.filter.displayName,
                        filterTextStartPositionX * Settings.xScale,
                        currentY * Settings.yScale,
                        filterTextLineWidth,
                        30.0f * Settings.yScale,
                        Settings.GOLD_COLOR);
                yDifference = getFilterStringHeight(filterListItem.filter);

                currentY -= 30.0F;
                FontHelper.renderSmartText(sb,
                        FontHelper.tipBodyFont,
                        filterListItem.filter.toString(),
                        filterTextStartPositionX * Settings.xScale,
                        currentY * Settings.yScale,
                        filterTextLineWidth,
                        30.0f * Settings.yScale,
                        Settings.CREAM_COLOR);
                sb.setColor(Color.WHITE);
                filterListItem.deleteButton.render(sb);
            }
        }
    }

    void update() {
        this.returnButton.update();

        for (FilterListItem filterListItem : filterList) {
            if (filterListItem.filter.shouldDisplay()){
                filterListItem.deleteButton.update();
            }
        }

        if (this.returnButton.hb.clickStarted){
            this.isShowing = false;
        }

        if (isShowing){
            this.returnButton.show();
        } else{
            this.returnButton.hide();
        }
    }

    private static class FilterListItem {
        private FilterListItem(AbstractFilter filter, ModLabeledButton deleteButton){
            this.filter = filter;
            this.deleteButton = deleteButton;
        }

        AbstractFilter filter;
        ModLabeledButton deleteButton;
    }
}
