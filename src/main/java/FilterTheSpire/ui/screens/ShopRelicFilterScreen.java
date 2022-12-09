package FilterTheSpire.ui.screens;

import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.FilterType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/*
    Shown when the user goes to Main Menu -> Mods -> Filter the Spire -> Config
 */
public class ShopRelicFilterScreen extends RelicFilterScreen {
    public ShopRelicFilterScreen() {
        super(AbstractRelic.RelicTier.SHOP, FilterType.NthShopRelic);
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
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "First Shop Relic", titleLeft * Settings.scale, titleBottom * Settings.scale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Shop Relics will appear in the first Shop. If no relics are selected, it will choose from the entire pool.",
                INFO_LEFT * Settings.scale,
                (INFO_TOP_MAIN * Settings.scale) + 120F,
                INFO_WIDTH * Settings.scale,
                30.0f * Settings.scale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Before Ascension 16: 143-157 gold NL Ascension 16+: 157-172 gold",
                INFO_LEFT * Settings.scale,
                (INFO_TOP_MAIN * Settings.scale) - 75F,
                INFO_WIDTH * Settings.scale,
                30.0f * Settings.scale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Controls: NL Click to toggle NL Right+Click to select just one NL NL Shift+Click to select all NL Shift+Right+Click to clear all NL Alt+Click to invert all",
                INFO_LEFT * Settings.scale,
                INFO_TOP_CONTROLS * Settings.scale,
                INFO_WIDTH * Settings.scale,
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
}
