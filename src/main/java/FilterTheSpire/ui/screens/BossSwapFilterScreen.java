package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.utils.ExtraFonts;
import basemod.ModLabeledToggleButton;
import basemod.ModToggleButton;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Shown when the user goes to Main Menu -> Mods -> Filter the Spire -> Config
 */
public class BossSwapFilterScreen implements IRelicFilterScreen {
    private TreeSet<String> bossRelics = new TreeSet<>();
    private HashMap<String, RelicUIObject> relicUIObjects = new HashMap<>();
    ModLabeledToggleButton neowBonusToggle;

    private static final float INFO_LEFT = 1120.0f;
    private static final float INFO_BOTTOM_CHECK = 670.0f;
    private static final float INFO_TOP_MAIN = INFO_BOTTOM_CHECK - 40.0f;
    private static final float INFO_TOP_CONTROLS = INFO_TOP_MAIN - 144.0f - 40.0f;

    private static final float INFO_WIDTH = 371.0f;

    public BossSwapFilterScreen() {
        setup();
    }

    private void populateRelics() {
        ArrayList<String> relics = new ArrayList<>();

        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.BOSS, AbstractPlayer.PlayerClass.IRONCLAD);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.BOSS, AbstractPlayer.PlayerClass.THE_SILENT);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.BOSS, AbstractPlayer.PlayerClass.DEFECT);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.BOSS, AbstractPlayer.PlayerClass.WATCHER);

        bossRelics.addAll(relics);
        removeClassUpgradedRelics();
    }

    // Don't allow unswappable relics to enter the pool
    private void removeClassUpgradedRelics() {
        Arrays.asList("Black Blood", "Ring of the Serpent", "FrozenCore", "HolyWater")
                .forEach(bossRelics::remove);
    }

    private void makeUIObjects() {
        // Note: relic textures are 128x128 originally, with some internal spacing
        float left = 410.0f;
        float top = 587.0f;

        float spacing = 84.0f;

        int ix = 0;
        int iy = 0;
        final int perRow = 5;

        for (String id : bossRelics) {
            float tx = left + ix * spacing;
            float ty = top - iy * spacing;

            relicUIObjects.put(id, new RelicUIObject(this, id, tx, ty));

            ix++;
            if (ix > perRow) {
                ix = 0;
                iy++;
            }
        }

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
    private void loadFromConfig() {
        ArrayList<String> loaded = FilterTheSpire.config.getBossSwapFilter();
        for (String relic : loaded) {
            if (relicUIObjects.containsKey(relic))
                relicUIObjects.get(relic).isEnabled = true;
        }

        refreshFilters();
    }

    private void setup() {
        populateRelics();
        makeUIObjects();
        loadFromConfig();
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        for (RelicUIObject x : relicUIObjects.values())
            x.render(sb);

        neowBonusToggle.render(sb);

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

    public void enableHitboxes(boolean enabled) {
        for (RelicUIObject obj : relicUIObjects.values()) {
            if (enabled)
                obj.enableHitbox();
            else
                obj.disableHitbox();
        }
    }

    public void render(SpriteBatch sb) {
        renderForeground(sb);
    }

    public void update() {
        for (RelicUIObject x : relicUIObjects.values()){
            x.update();
        }
        neowBonusToggle.update();
    }

    // --------------------------------------------------------------------------------

    public void clearAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = false;
        }

        refreshFilters();
    }

    private void select(String id) {
        if (relicUIObjects.containsKey(id)) {
            relicUIObjects.get(id).isEnabled = true;
            refreshFilters();
        }
    }

    public void selectOnly(String id) {
        if (relicUIObjects.containsKey(id)) {
            clearAll();
            relicUIObjects.get(id).isEnabled = true;
            refreshFilters();
        }
    }

    public void invertAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = !obj.isEnabled;
        }

        refreshFilters();
    }

    public void selectAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = true;
        }

        refreshFilters();
    }

    // --------------------------------------------------------------------------------

    public ArrayList<String> getEnabledRelics() {
        ArrayList<String> list = new ArrayList<>();

        for (RelicUIObject obj : relicUIObjects.values()) {
            if (obj.isEnabled)
                list.add(obj.relicID);
        }

        return list;
    }

    public void refreshFilters() {
        ArrayList<String> enabledRelics = getEnabledRelics();
        FilterTheSpire.config.setBossSwapFilter(enabledRelics);
        FilterManager.setBossSwapFiltersFromValidList(enabledRelics);
    }
}
