package FilterTheSpire.ui.screens;

import basemod.BaseMod;
import basemod.IUIElement;
import basemod.ModPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class AlternateConfigMenu extends ModPanel {
    private static Texture BACKGROUND = new Texture("FilterTheSpire/images/config.png");
    private BossSwapFilterScreen screen = new BossSwapFilterScreen();

    @Override
    public void renderBg(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(BACKGROUND,
                (Settings.WIDTH - (BACKGROUND.getWidth() * Settings.scale)) * 0.5f,
                (Settings.HEIGHT - (BACKGROUND.getHeight() * Settings.scale)) * 0.5f,
                BACKGROUND.getWidth() * Settings.scale,
                BACKGROUND.getHeight() * Settings.scale
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
