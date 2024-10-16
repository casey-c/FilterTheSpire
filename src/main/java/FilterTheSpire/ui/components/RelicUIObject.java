package FilterTheSpire.ui.components;

import FilterTheSpire.ui.screens.RelicBaseFilterScreen;
import FilterTheSpire.utils.ExtraColors;
import FilterTheSpire.utils.helpers.KeyHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RelicUIObject {

    private final Hitbox hb;

    public String relicID;
    private final float x;
    private final float y;
    private float scroll;
    private final Texture tex;
    private static final Texture TEX_SELECTED_BG = new Texture("FilterTheSpire/images/relic_bg.png");

    public boolean isEnabled = false;
    private final RelicBaseFilterScreen parent;

    public RelicUIObject(RelicBaseFilterScreen parent, AbstractRelic relic, float x, float y) {
        this.relicID = relic.relicId;
        this.tex = ImageMaster.getRelicImg(relicID);
        this.x = x;
        this.y = y;
        this.parent = parent;

        int hbSize = 75;
        hb = new Hitbox(hbSize * Settings.xScale, hbSize * Settings.yScale);
    }

    public void enableHitbox() {
        // Need to adjust them (hb are centered) -- this random guess is probably totally off
        int hbOffset = 50;
        hb.move((x + hbOffset) * Settings.xScale, (y + hbOffset + this.scroll) * Settings.yScale);
    }

    public void disableHitbox() {
        hb.move(-10000.0f, -10000.0f);
    }

    public void render(SpriteBatch sb) {
        // Grow a bit larger when hovered
        int size = 100;
        float s = (hb.hovered) ? size * 1.10f : size;

        float roundedScaledX = Math.round(x * Settings.xScale);
        float roundedScaledY = Math.round((y + this.scroll) * Settings.yScale);
        float roundedScaledW = Math.round(s * Settings.xScale);
        float roundedScaledH = Math.round(s * Settings.yScale);

        if (isEnabled) {
            sb.setColor(ExtraColors.SEL_RELIC_BG);
            sb.draw(TEX_SELECTED_BG,
                    roundedScaledX,
                    roundedScaledY,
                    roundedScaledW,
                    roundedScaledH);

            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(ExtraColors.DIM_RELIC);
        }

        sb.draw(tex,
                roundedScaledX,
                roundedScaledY,
                roundedScaledW,
                roundedScaledH);

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
            if (!parent.hasAddButton) {
                parent.updateFilters();
            }
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

    public void scroll(float scrollY) {
        this.scroll = scrollY;
    }

    public float getScrollPosition(){
        return (y + this.scroll) * Settings.yScale;
    }
}

