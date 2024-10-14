package FilterTheSpire.ui.screens;

import FilterTheSpire.ui.components.RelicUIObject;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.types.RunCheckpoint;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BossRelicFilterScreen extends RelicBaseFilterScreen {
    public BossRelicFilterScreen() {
        super(Collections.singletonList(AbstractRelic.RelicTier.BOSS), FilterType.NthBossRelic);
    }

    void postRelicSetup() {
        removeClassUpgradedRelics();
    }

    // Don't allow unswappable relics to enter the pool
    private void removeClassUpgradedRelics() {
        List<String> invalidSwaps = Arrays.asList("Black Blood", "Ring of the Serpent", "FrozenCore", "HolyWater");
        relics.removeIf(relic -> invalidSwaps.contains(relic.relicId));
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
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Neow Boss Swaps", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Boss Relics will appear from Neow's swap option. If no relics are selected, it will choose from the entire pool.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN + 100F) * Settings.yScale,
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
        for (RelicUIObject relic : relicUIObjects.values()){
            relic.update();
        }

        if (this.returnButton.hb.clickStarted){
            this.enableHitboxes(false);
        }
    }

    void setActOrEncounterIndex() {
        // Set this to a dropdown value when created
        filterObject.runCheckpoint = RunCheckpoint.NEOW;
    }
}
