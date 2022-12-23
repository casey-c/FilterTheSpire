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
    private NthRelicFilterScreen nthRelicFilterScreen = new NthRelicFilterScreen();
    private ModLabeledToggleButton neowBonusToggle;

    private ModLabeledButton bossRelicButton;
    private ModLabeledButton shopRelicButton;
    private ModLabeledButton neowBonusButton;
    private ModLabeledButton nthRelicButton;
    private boolean visible = false;

    public AlternateConfigMenu(){
        super();

        final float xPosition = 400.0F;
        float yPosition = FilterScreen.INFO_TOP_MAIN;

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

        yPosition -= 90.0F;
        nthRelicButton = new ModLabeledButton("Choose Relic Filter", xPosition, yPosition,
                Settings.CREAM_COLOR, Color.GOLD, FontHelper.tipHeaderFont,this,
                (self) -> { nthRelicFilterScreen.isShowing = true; });

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
                            FilterScreen.INFO_LEFT * Settings.xScale,
                            (FilterScreen.INFO_BOTTOM_CHECK - 40.0f) * Settings.yScale,
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
                Math.round((Settings.WIDTH - (TEX_BG.getWidth() * Settings.xScale)) * 0.5f),
                Math.round((Settings.HEIGHT - (TEX_BG.getHeight() * Settings.yScale)) * 0.5f),
                Math.round(TEX_BG.getWidth() * Settings.xScale),
                Math.round(TEX_BG.getHeight() * Settings.yScale)
        );

        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Filter Menu", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "You can choose which filters you want to apply. You can use them at the same time or neither.",
                FilterScreen.INFO_LEFT * Settings.xScale,
                FilterScreen.INFO_TOP_MAIN * Settings.yScale,
                FilterScreen.INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);
    }

    @Override
    public void render(SpriteBatch sb) {
        renderBg(sb);
        bossRelicButton.render(sb);
        shopRelicButton.render(sb);
        neowBonusButton.render(sb);
        nthRelicButton.render(sb);

        if (bossRelicScreen.isShowing) {
            bossRelicScreen.render(sb);
        } else if (shopRelicScreen.isShowing) {
            shopRelicScreen.render(sb);
        } else if (neowBonusScreen.isShowing) {
            neowBonusScreen.render(sb);
        } else if (nthRelicFilterScreen.isShowing) {
            nthRelicFilterScreen.render(sb);
        }else {
            neowBonusToggle.render(sb);
        }
    }

    @Override
    public void update() {
        if (bossRelicScreen.isShowing){
            bossRelicScreen.update();
        } else if (shopRelicScreen.isShowing) {
            shopRelicScreen.update();
        } else if (neowBonusScreen.isShowing) {
            neowBonusScreen.update();
        } else if (nthRelicFilterScreen.isShowing) {
            nthRelicFilterScreen.update();
        } else {
            neowBonusToggle.update();
            bossRelicButton.update();
            shopRelicButton.update();
            neowBonusButton.update();
            nthRelicButton.update();
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
