package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.utils.FilterType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class RelicFilterScreen extends FilterScreen {
    public TreeSet<String> relics = new TreeSet<>();
    public HashMap<String, RelicUIObject> relicUIObjects = new HashMap<>();

    private AbstractRelic.RelicTier relicScreenTier;
    private FilterType filterType;

    public RelicFilterScreen(AbstractRelic.RelicTier relicScreenTier, FilterType filterType){
        this.relicScreenTier = relicScreenTier;
        this.filterType = filterType;
        setup();
    }

    private void setup() {
        populateRelics();
        postRelicSetup();
        makeUIObjects();
        loadFromConfig();
    }

    protected void populateRelics() {
        ArrayList<String> relicPool = new ArrayList<>();

        RelicLibrary.populateRelicPool(relicPool, relicScreenTier, AbstractPlayer.PlayerClass.IRONCLAD);
        RelicLibrary.populateRelicPool(relicPool, relicScreenTier, AbstractPlayer.PlayerClass.THE_SILENT);
        RelicLibrary.populateRelicPool(relicPool, relicScreenTier, AbstractPlayer.PlayerClass.DEFECT);
        RelicLibrary.populateRelicPool(relicPool, relicScreenTier, AbstractPlayer.PlayerClass.WATCHER);

        relics.addAll(relicPool);
    }

    private void makeUIObjects() {
        // Note: relic textures are 128x128 originally, with some internal spacing
        float left = 410.0f;
        float top = 587.0f;

        float spacing = 84.0f;

        int ix = 0;
        int iy = 0;
        final int perRow = 5;

        for (String id : relics) {
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
        this.filterObject = FilterTheSpire.config.getFilter(this.filterType);
        for (String relic : filterObject.possibleValues) {
            if (relicUIObjects.containsKey(relic))
                relicUIObjects.get(relic).isEnabled = true;
        }

        refreshFilters();
    }

    public void enableHitboxes(boolean enabled) {
        for (RelicUIObject obj : relicUIObjects.values()) {
            if (enabled)
                obj.enableHitbox();
            else
                obj.disableHitbox();
        }

        if (enabled && isShowing){
            this.returnButton.show();
        } else{
            this.returnButton.hide();
            isShowing = false;
        }
    }

    protected void selectOnly(String id) {
        if (relicUIObjects.containsKey(id)) {
            clearAll();
            relicUIObjects.get(id).isEnabled = true;
            refreshFilters();
        }
    }

    protected void invertAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = !obj.isEnabled;
        }

        refreshFilters();
    }

    protected void selectAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = true;
        }

        refreshFilters();
    }

    protected void clearAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = false;
        }

        refreshFilters();
    }

    protected ArrayList<String> getEnabledRelics() {
        ArrayList<String> list = new ArrayList<>();

        for (RelicUIObject obj : relicUIObjects.values()) {
            if (obj.isEnabled)
                list.add(obj.relicID);
        }

        return list;
    }

    public void refreshFilters() {
        filterObject.possibleValues = getEnabledRelics();
        FilterTheSpire.config.updateFilter(filterObject);
        FilterManager.setFilter(filterObject);
    }

    abstract void postRelicSetup();
}
