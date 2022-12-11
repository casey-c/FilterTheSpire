package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.FilterType;
import basemod.ModLabeledToggleButton;
import basemod.ModToggleButton;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Arrays;

/*
    Shown when the user goes to Main Menu -> Mods -> Filter the Spire -> Config
 */
public class BossSwapFilterScreen extends RelicFilterScreen {
    public BossSwapFilterScreen() {
        super(AbstractRelic.RelicTier.BOSS, FilterType.NthBossRelic);
    }

    void postRelicSetup() {
        removeClassUpgradedRelics();
    }

    // Don't allow unswappable relics to enter the pool
    private void removeClassUpgradedRelics() {
        Arrays.asList("Black Blood", "Ring of the Serpent", "FrozenCore", "HolyWater")
                .forEach(relics::remove);
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        for (RelicUIObject relic : relicUIObjects.values()){
            relic.render(sb);
        }

        this.returnButton.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Neow Boss Swaps", titleLeft * Settings.scale, titleBottom * Settings.scale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Boss Relics will appear from Neow's swap option. If no relics are selected, it will choose from the entire pool.",
                INFO_LEFT * Settings.scale,
                INFO_TOP_MAIN * Settings.scale,
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
        for (RelicUIObject relic : relicUIObjects.values()){
            relic.update();
        }

        if (this.returnButton.hb.clickStarted){
            this.enableHitboxes(false);
        }
    }
}
