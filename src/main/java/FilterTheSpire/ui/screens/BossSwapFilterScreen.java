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
    private ModLabeledToggleButton neowBonusToggle;


    public BossSwapFilterScreen() {
        super(AbstractRelic.RelicTier.BOSS, FilterType.NthBossRelic);
    }

    void postSetup() {
        removeClassUpgradedRelics();

        neowBonusToggle = new ModLabeledToggleButton("Enable all Neow Bonuses",
                INFO_LEFT,         // NOTE: no scaling! (ModLabeledToggleButton scales later)
                INFO_BOTTOM_CHECK, // same as above
                Settings.CREAM_COLOR,
                FontHelper.charDescFont,
                FilterTheSpire.config.getBooleanKeyOrSetDefault("allNeowBonuses", true),
                null,
                (modLabel) -> {},
                (button) -> {
                    FilterTheSpire.config.setBooleanKey("allNeowBonuses", button.enabled);
                }) {
            // Override the update of the toggle button to add an informational tool tip when hovered
            @Override
            public void update() {
                super.update();

                // Unfortunately, the hb is private so we have to use reflection here
                Hitbox hb = ReflectionHacks.getPrivate(toggle, ModToggleButton.class, "hb");

                if (hb != null && hb.hovered) {
                    TipHelper.renderGenericTip(INFO_LEFT * Settings.scale, (INFO_BOTTOM_CHECK - 40.0f) * Settings.scale, "Info", "If checked, you will be guaranteed to see all four Neow options regardless of whether or not the previous run made it to the act one boss. NL NL Disabling this patch makes the experience more like the base game, but you may not have access to the boss swap option.");
                }
            }
        };
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
        this.neowBonusToggle.render(sb);

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
        neowBonusToggle.update();
    }
}
