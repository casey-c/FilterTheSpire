package FilterTheSpire.ui.screens;

import FilterTheSpire.ui.components.RelicUIObject;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.config.FilterType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Collections;

/*
    Shown when the user goes to Main Menu -> Mods -> Filter the Spire -> Config
 */
public class ShopRelicFilterScreen extends RelicFilterScreen {
    public ShopRelicFilterScreen() {
        super(Collections.singletonList(AbstractRelic.RelicTier.SHOP), FilterType.NthShopRelic);
    }

    void postRelicSetup() {}

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        for (RelicUIObject x : relicUIObjects.values())
            x.render(sb);

        this.returnButton.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "First Shop Relic", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Shop Relics will appear in the first Shop. If no relics are selected, it will choose from the entire pool.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN + 100F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Before Ascension 16: 143-157 gold NL Ascension 16+: 157-172 gold",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN - 75F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Controls: NL Click to toggle NL Right+Click to select just one NL NL Shift+Click to select all NL Shift+Right+Click to clear all NL Alt+Click to invert all",
                INFO_LEFT * Settings.xScale,
                INFO_TOP_CONTROLS * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Color.GRAY);
    }

    public void update() {
        this.returnButton.update();
        this.enableHitboxes(true);
        for (RelicUIObject x : relicUIObjects.values())
            x.update();

        if (this.returnButton.hb.clickStarted){
            this.enableHitboxes(false);
        }
    }
}
