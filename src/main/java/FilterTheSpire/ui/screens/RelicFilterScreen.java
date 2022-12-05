package FilterTheSpire.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runhistoryplus.ui.filters.ActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class RelicFilterScreen {
    public TreeSet<String> relics = new TreeSet<>();
    public HashMap<String, RelicUIObject> relicUIObjects = new HashMap<>();
    private Texture TEX_BG = new Texture("FilterTheSpire/images/config_screen_bg.png");
    public final ActionButton returnButton = new ActionButton(256, 450, "Back");
    public boolean isShowing = false;
    public static final float INFO_LEFT = 1120.0f;
    public static  final float INFO_BOTTOM_CHECK = 670.0f;
    public static  final float INFO_TOP_MAIN = INFO_BOTTOM_CHECK - 40.0f;
    public static final float INFO_TOP_CONTROLS = INFO_TOP_MAIN - 144.0f - 40.0f;
    public static  final float INFO_WIDTH = 371.0f;

    public RelicFilterScreen(AbstractRelic.RelicTier relicScreenTier){
        setup(relicScreenTier);
    }

    private void setup(AbstractRelic.RelicTier relicScreenTier) {
        populateRelics(relicScreenTier);
        makeUIObjects();
        loadFromConfig();
        postSetup();
    }

    protected void populateRelics(AbstractRelic.RelicTier relicScreenTier) {
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
        ArrayList<String> loaded = getFilter();
        for (String relic : loaded) {
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

    private void renderBg(SpriteBatch sb) {
        // Draw our screen texture in the center
        sb.setColor(Color.WHITE);
        sb.draw(TEX_BG,
                Math.round((Settings.WIDTH - (TEX_BG.getWidth() * Settings.scale)) * 0.5f),
                Math.round((Settings.HEIGHT - (TEX_BG.getHeight() * Settings.scale)) * 0.5f),
                Math.round(TEX_BG.getWidth() * Settings.scale),
                Math.round(TEX_BG.getHeight() * Settings.scale)
        );
    }

    public void render(SpriteBatch sb) {
        renderBg(sb);
        renderForeground(sb);
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

    abstract ArrayList<String> getFilter();
    abstract void postSetup();
    abstract void renderForeground(SpriteBatch sb);
    abstract void update();
    abstract void refreshFilters();
}
