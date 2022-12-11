package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.utils.ExtraColors;
import FilterTheSpire.utils.ExtraFonts;
import basemod.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class AlternateConfigMenu extends ModPanel {
    private static Texture TEX_BG = new Texture("FilterTheSpire/images/config_screen_bg.png");
    private BossSwapFilterScreen bossRelicScreen = new BossSwapFilterScreen();
    private ShopRelicFilterScreen shopRelicScreen = new ShopRelicFilterScreen();
    private NeowBonusFilterScreen neowBonusScreen = new NeowBonusFilterScreen();
    private ModLabeledToggleButton neowBonusToggle;

    private ModLabeledButton bossRelicButton;
    private ModLabeledButton shopRelicButton;
    private ModLabeledButton neowBonusButton;
    private boolean visible = false;

    public AlternateConfigMenu(){
        super();

        final float xPosition = 400.0F;
        float yPosition = 550.0F;

        // We should try and make it so we don't need to repeat this over and over
        bossRelicButton = new ModLabeledButton("Choose Boss Relics", xPosition, yPosition,
                Settings.CREAM_COLOR, Color.GOLD, FontHelper.tipHeaderFont,this,
                (self) -> { bossRelicScreen.isShowing = true; });

        yPosition -= 90.0F;
        shopRelicButton = new ModLabeledButton("Choose Shop Relics", xPosition, yPosition,
                Settings.CREAM_COLOR, Color.GOLD, FontHelper.tipHeaderFont,this,
                (self) -> { shopRelicScreen.isShowing = true; });

        yPosition -= 90.0F;
        neowBonusButton = new ModLabeledButton("Choose Neow Bonuses", xPosition, yPosition,
                Settings.CREAM_COLOR, Color.GOLD, FontHelper.tipHeaderFont,this,
                (self) -> { neowBonusScreen.isShowing = true; });

        neowBonusToggle = new ModLabeledToggleButton("Enable all Neow Bonuses",
                FilterScreen.INFO_LEFT,         // NOTE: no scaling! (ModLabeledToggleButton scales later)
                FilterScreen.INFO_BOTTOM_CHECK, // same as above
                Settings.CREAM_COLOR,
                FontHelper.charDescFont,
                FilterTheSpire.config.getBooleanKeyOrSetDefault("allNeowBonuses", true),
                null,
                (modLabel) -> {},
                (button) -> {
                    FilterTheSpire.config.setBooleanKey("allNeowBonuses", button.enabled);
                }) {
            // Override the update of the toggle button to add an informational tool tip when hovered
            @Override
            public void update() {
                super.update();

                // Unfortunately, the hb is private so we have to use reflection here
                Hitbox hb = ReflectionHacks.getPrivate(toggle, ModToggleButton.class, "hb");

                if (hb != null && hb.hovered) {
                    TipHelper.renderGenericTip(
                            FilterScreen.INFO_LEFT * Settings.scale,
                            (FilterScreen.INFO_BOTTOM_CHECK - 40.0f) * Settings.scale,
                            "Info",
                            "If checked, you will be guaranteed to see all four Neow options regardless of " +
                                    "whether or not the previous run made it to the act one boss. NL NL Disabling this " +
                                    "patch makes the experience more like the base game, but you may not have access " +
                                    "to the boss swap option.");
                }
            }
        };
    }

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

        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Filter Menu", titleLeft * Settings.scale, titleBottom * Settings.scale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "You can choose which filters you want to apply. You can use them at the same time or neither.",
                FilterScreen.INFO_LEFT * Settings.scale,
                FilterScreen.INFO_TOP_MAIN * Settings.scale,
                FilterScreen.INFO_WIDTH * Settings.scale,
                30.0f * Settings.scale,
                Settings.CREAM_COLOR);
    }

    @Override
    public void render(SpriteBatch sb) {
        renderBg(sb);
        neowBonusToggle.render(sb);
        bossRelicButton.render(sb);
        shopRelicButton.render(sb);
        neowBonusButton.render(sb);

        if (bossRelicScreen.isShowing) {
            bossRelicScreen.render(sb);
        } else if (shopRelicScreen.isShowing) {
            shopRelicScreen.render(sb);
        } else if (neowBonusScreen.isShowing) {
            neowBonusScreen.render(sb);
        }
    }

    @Override
    public void update() {
        neowBonusToggle.update();
        if (bossRelicScreen.isShowing){
            bossRelicScreen.update();
            bossRelicScreen.enableHitboxes(true);
        } else if (shopRelicScreen.isShowing) {
            shopRelicScreen.update();
            shopRelicScreen.enableHitboxes(true);
        } else if (neowBonusScreen.isShowing) {
            neowBonusScreen.update();
        } else {
            bossRelicButton.update();
            shopRelicButton.update();
            neowBonusButton.update();
        }

        if (InputHelper.pressedEscape) {
            BaseMod.modSettingsUp = false;
            InputHelper.pressedEscape = false;
        }

        if (!BaseMod.modSettingsUp) {
            this.waitingOnEvent = false;
            bossRelicScreen.isShowing = false;
            shopRelicScreen.isShowing = false;
            Gdx.input.setInputProcessor(this.oldInputProcessor);
            CardCrawlGame.mainMenuScreen.lighten();
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            CardCrawlGame.cancelButton.hideInstantly();
            this.isUp = false;
        }

        // Enable and disable hitboxes
        if (isUp && !visible) {
            visible = true;
        }
        else if (!isUp && visible) {
            visible = false;
        }
    }
}
