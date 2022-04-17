package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.utils.ExtraFonts;
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

/*
    Shown when the user goes to Main Menu -> Mods -> Filter the Spire -> Config
 */
public class ShopRelicFilterScreen implements IRelicFilterScreen {

    private TreeSet<String> shopRelics = new TreeSet<>();
    private HashMap<String, RelicUIObject> relicUIObjects = new HashMap<>();

    public ShopRelicFilterScreen() {
        setup();
    }

    private void populateRelics() {
        ArrayList<String> relics = new ArrayList<>();

        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.SHOP, AbstractPlayer.PlayerClass.IRONCLAD);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.SHOP, AbstractPlayer.PlayerClass.THE_SILENT);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.SHOP, AbstractPlayer.PlayerClass.DEFECT);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.SHOP, AbstractPlayer.PlayerClass.WATCHER);

        shopRelics.addAll(relics);
    }

    private void makeUIObjects() {
        // Note: relic textures are 128x128 originally, with some internal spacing
        float left = 410.0f;
        float top = 587.0f;

        float spacing = 84.0f;

        int ix = 0;
        int iy = 0;
        final int perRow = 5;

        for (String id : shopRelics) {
            float tx = left + ix * spacing;
            float ty = top - iy * spacing;

            relicUIObjects.put(id, new RelicUIObject(this, id, tx, ty));

            ix++;
            if (ix > perRow) {
                ix = 0;
                iy++;
            }
        }
    }
    private void loadFromConfig() {
        ArrayList<String> loaded = FilterTheSpire.config.getShopRelicFilter();
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
        for (RelicUIObject x : relicUIObjects.values())
            x.update();
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
        FilterTheSpire.config.setShopRelicFilter(enabledRelics);
        FilterManager.setShopFiltersFromValidList(enabledRelics, 0);
    }
}
