package FilterTheSpire.ui.screens;

import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.ui.components.RelicUIObject;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.types.RunCheckpoint;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BossRelicFilterScreen extends RelicBaseFilterScreen {
    private final DropdownMenu checkpointDropdown;
    private final List<String> invalidSwaps = Arrays.asList("Black Blood", "Ring of the Serpent", "FrozenCore", "HolyWater");

    public BossRelicFilterScreen(ModPanel p) {
        super(Collections.singletonList(AbstractRelic.RelicTier.BOSS), FilterType.NthBossRelic, p, true);
        checkpointDropdown = new DropdownMenu(null,
                new ArrayList<>(Arrays.asList("Neow Boss Swap", "Act 1 Boss", "Act 2 Boss")),
                FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
    }

    private RunCheckpoint getCheckpointFromDropdownIndex(){
        if (checkpointDropdown != null){
            int index = checkpointDropdown.getSelectedIndex();
            switch (index){
                case 0:
                    return RunCheckpoint.NEOW;
                case 1:
                    return RunCheckpoint.ACT1_BOSS;
                case 2:
                    return RunCheckpoint.ACT2_BOSS;
            }
        }
        return RunCheckpoint.NEOW;
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        for (RelicUIObject relic : relicUIObjects.values()){
            relic.render(sb);
        }

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Boss Relic Filter", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Boss Relics will appear from different points in the run. If no relics are selected, it will choose from the entire pool.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN + 100F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Starter relic replacements cannot be retrieved from a Neow Boss Swap.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN - 50.0F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.RED_TEXT_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Controls: NL Click to toggle NL Right+Click to select just one NL NL Shift+Click to select all NL Shift+Right+Click to clear all NL Alt+Click to invert all",
                INFO_LEFT * Settings.xScale,
                INFO_TOP_CONTROLS * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Color.GRAY);

        // top bar controls
        float xPosition = BaseFilterScreen.INFO_LEFT - 90.0f;
        float yPosition = 850.0f;
        this.checkpointDropdown.render(sb, xPosition * Settings.xScale, yPosition * Settings.yScale);
        FontHelper.renderSmartText(sb,
                FontHelper.tipHeaderFont,
                "Encounter:",
                (xPosition - 140.0f) * Settings.xScale,
                (yPosition - 5.0f) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

    }

    public void update() {
        this.addButton.update();
        this.returnButton.update();
        this.checkpointDropdown.update();
        this.enableHitboxes(true);
        for (RelicUIObject relic : relicUIObjects.values()){
            relic.update();
        }

        if (this.returnButton.hb.clickStarted){
            this.enableHitboxes(false);
        }
    }

    public void setFilterObjectForAddOrUpdate(){
        filterObject = new FilterObject(FilterType.NthBossRelic);
        filterObject.runCheckpoint = getCheckpointFromDropdownIndex();
    }
}
