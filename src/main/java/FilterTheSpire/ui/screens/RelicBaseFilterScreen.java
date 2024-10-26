package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.ui.components.RelicUIObject;
import FilterTheSpire.utils.config.FilterType;
import basemod.ModPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.*;

public abstract class RelicBaseFilterScreen extends BaseFilterScreen {
    public TreeSet<AbstractRelic> relics = new TreeSet<>();
    public HashMap<String, RelicUIObject> relicUIObjects = new HashMap<>();

    private final List<AbstractRelic.RelicTier> relicScreenTiers;

    public RelicBaseFilterScreen(List<AbstractRelic.RelicTier> relicScreenTiers, FilterType filterType, ModPanel p,
                                 boolean hasAddButton){
        super(filterType, p, hasAddButton);
        this.relicScreenTiers = relicScreenTiers;
        setup();
    }

    public void open(){
        setup();
        isShowing = true;
    }

    private void setup() {
        populateRelics();
        postRelicSetup();
        makeUIObjects();
    }

    protected void populateRelics() {
        ArrayList<String> relicPool = new ArrayList<>();

        for (AbstractRelic.RelicTier tier: relicScreenTiers) {
            RelicLibrary.populateRelicPool(relicPool, tier, AbstractPlayer.PlayerClass.IRONCLAD);
            RelicLibrary.populateRelicPool(relicPool, tier, AbstractPlayer.PlayerClass.THE_SILENT);
            RelicLibrary.populateRelicPool(relicPool, tier, AbstractPlayer.PlayerClass.DEFECT);
            RelicLibrary.populateRelicPool(relicPool, tier, AbstractPlayer.PlayerClass.WATCHER);
        }

        List<AbstractRelic> relicObjects = new ArrayList<>();
        for (String relicId: relicPool) {
            AbstractRelic relic = RelicLibrary.getRelic(relicId);
            relicObjects.add(relic);
        }
        relicObjects.sort(Comparator.comparing(relic -> relic.name));
        this.relics.addAll(relicObjects);
    }

    private void makeUIObjects() {
        // Note: relic textures are 128x128 originally, with some internal spacing
        float left = 410.0f;
        float top = 587.0f;

        float spacing = 84.0f;

        int ix = 0;
        int iy = 0;
        final int perRow = 5;

        for (AbstractRelic relic : relics) {
            float tx = left + ix * spacing;
            float ty = top - iy * spacing;

            relicUIObjects.put(relic.relicId, new RelicUIObject(this, relic, tx, ty));

            ix++;
            if (ix > perRow) {
                ix = 0;
                iy++;
            }
        }
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

    public void selectOnly(String id) {
        if (relicUIObjects.containsKey(id)) {
            clearAll();
            relicUIObjects.get(id).isEnabled = true;
        }
    }

    public void invertAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = !obj.isEnabled;
        }
    }

    public void selectAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = true;
        }
    }

    public void clearAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = false;
        }
    }

    protected ArrayList<String> getEnabledRelics() {
        ArrayList<String> list = new ArrayList<>();

        for (RelicUIObject obj : relicUIObjects.values()) {
            if (obj.isEnabled)
                list.add(obj.relicID);
        }

        return list;
    }

    public void updateFilters() {
        setFilterObjectForAddOrUpdate();
        filterObject.possibleValues = getEnabledRelics();
        FilterTheSpire.config.addOrUpdateFilter(filterObject);
        FilterManager.addFilter(filterObject);
    }

    public void resetUI(){
        clearAll();
    }

    abstract void postRelicSetup();

}
