package FilterTheSpire.ui.screens;

import FilterTheSpire.utils.ExtraColors;
import basemod.BaseMod;
import basemod.ModPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class AlternateConfigMenu extends ModPanel {
    private static Texture TEX_BG = new Texture("FilterTheSpire/images/config_screen_bg.png");
    private BossSwapFilterScreen screen = new BossSwapFilterScreen();

    @Override
    public void renderBg(SpriteBatch sb) {
        // Dim to diminish the rest of the config menu
        sb.setColor(ExtraColors.SCREEN_DIM);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);

        // Draw our screen texture in the center
        sb.setColor(Color.WHITE);
        sb.draw(TEX_BG,
                Math.round((Settings.WIDTH - (TEX_BG.getWidth() * Settings.scale)) * 0.5f),
                Math.round((Settings.HEIGHT - (TEX_BG.getHeight() * Settings.scale)) * 0.5f),
                Math.round(TEX_BG.getWidth() * Settings.scale),
                Math.round(TEX_BG.getHeight() * Settings.scale)
        );

    }

    @Override
    public void render(SpriteBatch sb) {
        renderBg(sb);

        // Render kids
        screen.render(sb);
    }

    private boolean visible = false;

    @Override
    public void update() {
        // TODO: Update all children
        screen.update();

        if (InputHelper.pressedEscape) {
            InputHelper.pressedEscape = false;
            BaseMod.modSettingsUp = false;
        }

        if (!BaseMod.modSettingsUp) {
            this.waitingOnEvent = false;
            Gdx.input.setInputProcessor(this.oldInputProcessor);
            CardCrawlGame.mainMenuScreen.lighten();
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            CardCrawlGame.cancelButton.hideInstantly();
            this.isUp = false;
        }

        // Enable and disable hitboxes
        if (isUp && !visible) {
            visible = true;
            screen.enableHitboxes(true);
        }
        else if (!isUp && visible) {
            visible = false;
            screen.enableHitboxes(false);
        }

    }
}
