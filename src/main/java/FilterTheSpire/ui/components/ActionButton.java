package FilterTheSpire.ui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class ActionButton {
    public boolean isHidden;
    public Hitbox hb;
    public float current_x;
    private float target_x;
    private float glowAlpha;
    private Color glowColor;
    private String buttonText;
    private float drawX;
    public float hideX;
    private float drawY;
    private static final float TEXT_OFFSET_X;
    private static final float TEXT_OFFSET_Y;

    public ActionButton(int drawX, int drawY, String buttonText) {
        this.drawX = drawX * Settings.xScale;
        this.hideX = this.drawX - 400.0F * Settings.xScale;
        this.current_x = this.target_x = this.hideX;
        this.isHidden = true;
        this.glowAlpha = 0.0F;
        this.glowColor = Settings.GOLD_COLOR.cpy();
        this.buttonText = buttonText;
        this.drawY = drawY * Settings.yScale;
        this.hb = new Hitbox(300.0F * Settings.xScale, 100.0F * Settings.yScale);
        this.hb.move(this.drawX - 106.0F * Settings.xScale, this.drawY + 60.0F * Settings.yScale);
    }

    public void update(){
        if (!this.isHidden) {
            this.updateGlow();
            this.hb.update();
            if (InputHelper.justClickedLeft && this.hb.hovered) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }

            if (this.hb.justHovered) {
                CardCrawlGame.sound.play("UI_HOVER");
            }

            if (CInputActionSet.cancel.isJustPressed()) {
                this.hb.clicked = true;
            }
        }

        if (this.current_x != this.target_x) {
            this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
            if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD) {
                this.current_x = this.target_x;
            }
        }
    }

    private void updateGlow() {
        this.glowAlpha += Gdx.graphics.getDeltaTime() * 3.0F;
        if (this.glowAlpha < 0.0F) {
            this.glowAlpha *= -1.0F;
        }

        float tmp = MathUtils.cos(this.glowAlpha);
        if (tmp < 0.0F) {
            this.glowColor.a = -tmp / 2.0F + 0.3F;
        } else {
            this.glowColor.a = tmp / 2.0F + 0.3F;
        }

    }

    public void render(SpriteBatch sb){
        sb.setColor(Color.WHITE);
        this.renderShadow(sb);
        sb.setColor(this.glowColor);
        this.renderOutline(sb);
        sb.setColor(Color.WHITE);
        this.renderButton(sb);
        if (this.hb.hovered && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.4F));
            this.renderButton(sb);
            sb.setBlendFunction(770, 771);
        }

        Color tmpColor = Settings.LIGHT_YELLOW_COLOR;
        if (this.hb.clickStarted) {
            tmpColor = Color.LIGHT_GRAY;
        }

        if (Settings.isControllerMode) {
            FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TEXT_OFFSET_X - 30.0F * Settings.xScale, this.drawY + TEXT_OFFSET_Y, tmpColor);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TEXT_OFFSET_X, this.drawY + TEXT_OFFSET_Y, tmpColor);
        }

        this.renderControllerUi(sb);
        if (!this.isHidden) {
            this.hb.render(sb);
        }
    }

    private void renderShadow(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON_SHADOW, this.current_x - 256.0F, this.drawY - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderOutline(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON_OUTLINE, this.current_x - 256.0F, this.drawY - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderButton(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON, this.current_x - 256.0F, this.drawY - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderControllerUi(SpriteBatch sb) {
        if (Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.cancel.getKeyImg(), this.current_x - 32.0F - 210.0F * Settings.xScale, this.drawY - 32.0F + 57.0F * Settings.yScale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 64, 64, false, false);
        }

    }

    public void show() {
        this.current_x = this.drawX;
        if (this.isHidden) {
            this.glowAlpha = 0.0F;
            this.target_x = this.drawX;
            this.isHidden = false;
        }
    }

    public void hide(){
        if (!this.isHidden) {
            this.hb.clicked = false;
            this.hb.clickStarted = false;
            this.hb.hovered = false;
            InputHelper.justClickedLeft = false;
            this.current_x = this.hideX;
            this.target_x = this.hideX;
            this.isHidden = true;
        }
    }

    static {
        TEXT_OFFSET_X = -136.0F * Settings.xScale;
        TEXT_OFFSET_Y = 57.0F * Settings.yScale;
    }
}
