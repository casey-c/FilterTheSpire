package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.utils.ExtraFonts;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

/*
    Shown when the user goes to Main Menu -> Mods -> Filter the Spire -> Config
 */
public class ShopRelicFilterScreen extends RelicFilterScreen {
    public boolean isShowing = false;

    public ShopRelicFilterScreen() {
        super(AbstractRelic.RelicTier.SHOP);
    }

    ArrayList<String> getFilter() {
        return FilterTheSpire.config.getShopRelicFilter();
    }

    void postSetup() {}

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        for (RelicUIObject x : relicUIObjects.values())
            x.render(sb);

        this.returnButton.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "First Shop Relic", titleLeft * Settings.scale, titleBottom * Settings.scale, Settings.GOLD_COLOR);

        float infoLeft = 1120.0f;
        float infoTopMain = 667.0f;
        float infoTopControls = 472.0f;

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Shop Relics will appear in the first Shop. If no relics are selected, it will choose from the entire pool.",
                infoLeft * Settings.scale,
                infoTopMain * Settings.scale,
                371.0f * Settings.scale,
                30.0f * Settings.scale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Controls: NL Click to toggle NL Right+Click to select just one NL NL Shift+Click to select all NL Shift+Right+Click to clear all NL Alt+Click to invert all",
                infoLeft * Settings.scale,
                infoTopControls * Settings.scale,
                371.0f * Settings.scale,
                30.0f * Settings.scale,
                Color.GRAY);
    }

    public void update() {
        this.returnButton.update();
        for (RelicUIObject x : relicUIObjects.values())
            x.update();

        if (this.returnButton.hb.clickStarted){
            this.enableHitboxes(false);
        }
    }

    public void refreshFilters() {
        ArrayList<String> enabledRelics = getEnabledRelics();
        FilterTheSpire.config.setShopRelicFilter(enabledRelics);
        FilterManager.setShopFiltersFromValidList(enabledRelics, 0);
    }

    public void show() {
        this.isShowing = true;
    }
}
