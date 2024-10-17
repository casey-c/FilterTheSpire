package FilterTheSpire.ui.screens;

import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.ui.components.RelicUIObject;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.config.FilterType;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;

import java.util.Collections;
import java.util.stream.IntStream;

public class ShopRelicFilterScreen extends RelicBaseFilterScreen {
    private final DropdownMenu encounterDropdown;
    public ShopRelicFilterScreen(ModPanel p) {
        super(Collections.singletonList(AbstractRelic.RelicTier.SHOP), FilterType.NthShopRelic, p, true);
        String[] shopEncounters = IntStream.range(1, 6).mapToObj(String::valueOf).toArray(String[]::new);
        encounterDropdown = new DropdownMenu(null,
                shopEncounters,
                FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
    }

    void postRelicSetup() {}

    public void render(SpriteBatch sb){
        super.render(sb);
        sb.setColor(Color.WHITE);
        this.addButton.render(sb);
        this.returnButton.render(sb);
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        for (RelicUIObject x : relicUIObjects.values()) {
            x.render(sb);
        }

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Shop Relic Filter", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

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
                (INFO_TOP_MAIN - 40F) * Settings.yScale,
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

        // top bar controls
        float xPosition = BaseFilterScreen.INFO_LEFT + 50.0f;
        float yPosition = 850.0f;
        this.encounterDropdown.render(sb, xPosition * Settings.xScale, yPosition * Settings.yScale);
        FontHelper.renderSmartText(sb,
                FontHelper.tipHeaderFont,
                "Shop Encounter:",
                (xPosition - 200.0f) * Settings.xScale,
                (yPosition - 5.0f) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);
    }

    public void update() {
        this.addButton.update();
        this.returnButton.update();
        this.encounterDropdown.update();
        this.enableHitboxes(true);
        for (RelicUIObject x : relicUIObjects.values())
            x.update();

        if (this.returnButton.hb.clickStarted){
            this.enableHitboxes(false);
        }
    }

    public void setFilterObjectForAddOrUpdate() {
        filterObject = new FilterObject(FilterType.NthShopRelic);
        filterObject.possibleEncounterIndices = Collections.singletonList(encounterDropdown.getSelectedIndex());
    }
}
