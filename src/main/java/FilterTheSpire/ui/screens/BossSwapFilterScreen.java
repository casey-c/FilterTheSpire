package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.utils.ExtraColors;
import FilterTheSpire.utils.KeyHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
    Shown when the user goes to Main Menu -> Mods -> Filter the Spire -> Config
 */
public class BossSwapFilterScreen {
    // TODO: hitboxes, etc.
    private static class RelicUIObject {
        private int size = 100;
        private int hbSize = 75;
        private int hbOffset = 50;

        private Hitbox hb;

        private String relicID;
        private float x, y;
        private Texture tex;
        private static final Texture relicBg = new Texture("FilterTheSpire/images/relic_bg.png");

        private boolean isEnabled = false;
        private BossSwapFilterScreen parent;

        public RelicUIObject(BossSwapFilterScreen parent, String relicID, float x, float y) {
            this.relicID = relicID;
            this.tex = ImageMaster.getRelicImg(relicID);
            this.x = x;
            this.y = y;
            this.parent = parent;

            hb = new Hitbox(hbSize, hbSize);
        }

        public void enableHitbox() {
            // Need to adjust them (hb are centered) -- this random guess is probably totally off
            hb.move(x + hbOffset, y + hbOffset);
        }

        public void disableHitbox() {
            hb.move(-10000.0f, -10000.0f);
        }

        public void render(SpriteBatch sb) {
            // Grow a bit larger when hovered
            float s = (hb.hovered) ? size * 1.10f : size;

            if (isEnabled) {
                sb.setColor(ExtraColors.SEL_RELIC_BG);
                sb.draw(relicBg, x * Settings.scale, y * Settings.scale, s * Settings.scale, s * Settings.scale);

                sb.setColor(Color.WHITE);
            } else {
                sb.setColor(ExtraColors.DIM_RELIC);
            }


            sb.draw(tex, x * Settings.scale, y * Settings.scale, s * Settings.scale, s * Settings.scale);

            // DEBUG
            hb.render(sb);
        }

        public void handleClick() {
            System.out.println("just clicked " + relicID);
            if (isEnabled)
                CardCrawlGame.sound.playA("UI_CLICK_1", 0.2f);
            else
                CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);

            if (KeyHelper.isShiftPressed()) {
                parent.selectOnly(relicID);
            }
            else {
                isEnabled = !isEnabled;
                parent.refreshFilters();
            }
        }

        public void update() {
            hb.update();

            if (hb.justHovered) {
                CardCrawlGame.sound.playA("UI_HOVER", -0.5f);
            }

            if (this.hb.hovered && InputHelper.justClickedLeft) {
                this.hb.clickStarted = true;
            }

            if (hb.clicked) {
                hb.clicked = false;
                handleClick();
            }

        }
    }

    private HashSet<String> bossRelics = new HashSet<>();
    private HashMap<String, RelicUIObject> relicUIObjects = new HashMap<>();
    private boolean alreadySetup = false;

    private void populateRelics() {
        ArrayList<String> relics = new ArrayList<>();

        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.BOSS, AbstractPlayer.PlayerClass.IRONCLAD);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.BOSS, AbstractPlayer.PlayerClass.THE_SILENT);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.BOSS, AbstractPlayer.PlayerClass.DEFECT);
        RelicLibrary.populateRelicPool(relics, AbstractRelic.RelicTier.BOSS, AbstractPlayer.PlayerClass.WATCHER);

        bossRelics.addAll(relics);
        System.out.println("There are " + bossRelics.size() + " boss relics in the pool.");
    }

    private void makeUIObjects() {
        // Note: relic textures are 128x128 originally, with some internal spacing
        float left = 400.0f;
        float bottom = 319.0f - 60.0f;

        float spacing = 84.0f;

        int ix = 0;
        int iy = 0;
        final int perRow = 5;

        for (String id : bossRelics) {
            float tx = left + ix * spacing;
            float ty = bottom + iy * spacing;

            relicUIObjects.put(id, new RelicUIObject(this, id, tx, ty));

            ix++;
            if (ix > perRow) {
                ix = 0;
                iy++;
            }
        }
    }

    private void setup() {
        populateRelics();
        makeUIObjects();
        alreadySetup = true;
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        for (RelicUIObject x : relicUIObjects.values())
            x.render(sb);

        // Render the text
        float leftTextX = 426.0f;
        float topTextY = 863.0f + 18.0f - 60.0f;

        FontHelper.renderFontLeftDownAligned(sb, FontHelper.topPanelAmountFont, "Boss Swap Filter (WIP)", leftTextX, topTextY, Settings.CREAM_COLOR);
        FontHelper.renderFontLeftDownAligned(sb, FontHelper.tipBodyFont, "Click to toggle enabled relics", leftTextX, topTextY - 50.0f, Color.GRAY);
        FontHelper.renderFontLeftDownAligned(sb, FontHelper.tipBodyFont, "Shift+Click to force just one relic", leftTextX, topTextY - 87.0f, Color.GRAY);

        float rightTextLeft = 1040.0f;
        float rightTextTop = 724.0f - 60.0f;
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, "This filter will let you choose which relics will be available from Neow's Boss Swap option. NL NL When starting a new run, only seeds that can swap into any of the selected relics will be available. If no relics are selected, the game will choose from the entire set.", rightTextLeft, rightTextTop, 444.0f, 30.0f, Color.GRAY);
    }

    public void enableHitboxes(boolean enabled) {
        System.out.println("Setting hitboxes to be enabled: " + enabled);

        for (RelicUIObject obj : relicUIObjects.values()) {
            if (enabled)
                obj.enableHitbox();
            else
                obj.disableHitbox();
        }
    }

    public void render(SpriteBatch sb) {
        if (!alreadySetup)
            setup();

        renderForeground(sb);
    }

    public void update() {
        for (RelicUIObject x : relicUIObjects.values())
            x.update();
    }

    // --------------------------------------------------------------------------------

    private void clearAll() {
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

    private void selectOnly(String id) {
        if (relicUIObjects.containsKey(id)) {
            clearAll();
            relicUIObjects.get(id).isEnabled = true;
            refreshFilters();
        }
    }

    // --------------------------------------------------------------------------------

    public ArrayList<String> getEnabledRelics() {
        ArrayList<String> list = new ArrayList<>();

        for (RelicUIObject obj : relicUIObjects.values()) {
            if (obj.isEnabled)
                list.add(obj.relicID);
        }

        if (list.isEmpty())
            list.addAll(relicUIObjects.keySet());

        return list;
    }

    public void refreshFilters() {
        FilterManager.setBossSwapFiltersFromValidList(getEnabledRelics());
    }
}
