package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.utils.ExtraFonts;
import basemod.ModLabeledToggleButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.function.Consumer;

/*
    Shown when the user goes to Main Menu -> Mods -> Filter the Spire -> Config
 */
public class BossSwapFilterScreen implements IRelicFilterScreen {
    private TreeSet<String> bossRelics = new TreeSet<>();
    private HashMap<String, RelicUIObject> relicUIObjects = new HashMap<>();
    ModLabeledToggleButton neowBonusToggle;

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
        Consumer<String> remove = (relicName) -> {
            if (bossRelics.contains(relicName))
                bossRelics.remove(relicName);
        };

        remove.accept("Black Blood");
        remove.accept("Ring of the Serpent");
        remove.accept("FrozenCore");
        remove.accept("HolyWater");
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

        neowBonusToggle = new ModLabeledToggleButton("Enable all Neow Bonuses", 840.0f * Settings.scale,
                500.0f * Settings.scale, Settings.CREAM_COLOR, FontHelper.charDescFont,
                FilterTheSpire.config.getBooleanKey("allNeowBonuses"), null, (modLabel) -> {}, (button) -> {
                    FilterTheSpire.config.setBooleanKey("allNeowBonuses", button.enabled);
                });
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

        float infoLeft = 1120.0f;
        float infoTopMain = 610.0f;
        float infoTopControls = 472.0f;

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Boss Relics will appear from Neow's swap option. If no relics are selected, it will choose from the entire pool.",
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
