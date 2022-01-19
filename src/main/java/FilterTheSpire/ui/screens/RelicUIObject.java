package FilterTheSpire.ui.screens;

import FilterTheSpire.ui.screens.IRelicFilterScreen;
import FilterTheSpire.utils.ExtraColors;
import FilterTheSpire.utils.KeyHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class RelicUIObject {
    private int size = 100;
    private int hbSize = 75;
    private int hbOffset = 50;

    private Hitbox hb;

    public String relicID;
    private float x, y;
    private Texture tex;
    private static final Texture TEX_SELECTED_BG = new Texture("FilterTheSpire/images/relic_bg.png");

    public boolean isEnabled = false;
    private IRelicFilterScreen parent;

    public RelicUIObject(IRelicFilterScreen parent, String relicID, float x, float y) {
        this.relicID = relicID;
        this.tex = ImageMaster.getRelicImg(relicID);
        this.x = x;
        this.y = y;
        this.parent = parent;

        hb = new Hitbox(hbSize * Settings.scale, hbSize * Settings.scale);
    }

    public void enableHitbox() {
        // Need to adjust them (hb are centered) -- this random guess is probably totally off
        hb.move((x + hbOffset) * Settings.scale, (y + hbOffset) * Settings.scale);
    }

    public void disableHitbox() {
        hb.move(-10000.0f, -10000.0f);
    }

    public void render(SpriteBatch sb) {
        // Grow a bit larger when hovered
        float s = (hb.hovered) ? size * 1.10f : size;

        if (isEnabled) {
            sb.setColor(ExtraColors.SEL_RELIC_BG);
            sb.draw(TEX_SELECTED_BG, x * Settings.scale, y * Settings.scale, s * Settings.scale, s * Settings.scale);

            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(ExtraColors.DIM_RELIC);
        }


        sb.draw(tex, x * Settings.scale, y * Settings.scale, s * Settings.scale, s * Settings.scale);

        // DEBUG
        hb.render(sb);
    }

    private void handleClick() {
        if (KeyHelper.isShiftPressed()) {
            CardCrawlGame.sound.play("BLOOD_SPLAT");
            parent.selectAll();
        }
        else if (KeyHelper.isAltPressed()) {
            CardCrawlGame.sound.play("MAP_SELECT_3");
            parent.invertAll();
        }
        else {
            if (isEnabled) CardCrawlGame.sound.playA("UI_CLICK_1", 0.2f);
            else CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);

            isEnabled = !isEnabled;
            parent.refreshFilters();
        }
    }

    private void handleRightClick() {
        if (KeyHelper.isShiftPressed()) {
            CardCrawlGame.sound.play("APPEAR");
            parent.clearAll();
        }
        else {
            CardCrawlGame.sound.play("KEY_OBTAIN");
            parent.selectOnly(relicID);
        }
    }

    private boolean mouseDownRight = false;

    public void update() {
        hb.update();

        if (hb.justHovered) {
            CardCrawlGame.sound.playAV("UI_HOVER", -0.4f, 0.5f);
        }

        // Right clicks
        if (hb.hovered && InputHelper.isMouseDown_R) {
            mouseDownRight = true;
        } else {
            // We already had the mouse down, and now we released, so fire our right click event
            if (hb.hovered && mouseDownRight) {
                handleRightClick();
                mouseDownRight = false;
            }
        }

        // Left clicks
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            this.hb.clickStarted = true;
        }

        if (hb.clicked) {
            hb.clicked = false;
            handleClick();
        }

    }
}

