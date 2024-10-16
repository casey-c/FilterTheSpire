package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.patches.DropdownMenuPatch;
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
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.vfx.SpeechTextEffect;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class AlternateConfigMenu extends ModPanel implements DropdownMenuListener {
    private static final Texture TEX_BG = new Texture("FilterTheSpire/images/config_screen_bg.png");
    private final ModLabeledToggleButton neowBonusToggle;
    private final DropdownMenu threadCountDropdown;

    private final ArrayList<ModLabeledButton> filterButtons = new ArrayList<>();
    private final ArrayList<BaseFilterScreen> baseFilterScreens = new ArrayList<>();
    private final ArrayList<BaseScreen> allScreens = new ArrayList<>();
    private ModLabeledButton clearButton;
    SpeechTextEffect clearMessage;

    private boolean visible = false;

    public AlternateConfigMenu() {
        super();

        final float xPosition = 400.0F;
        float yPosition = BaseFilterScreen.INFO_TOP_MAIN;
        final float yIncrement = 90.0F;
        final float xIncrement = 330.0F;

        BossRelicFilterScreen bossRelicScreen = new BossRelicFilterScreen(this);
        filterButtons.add(createFilterScreenButton("Choose Boss Relics", xPosition, yPosition, bossRelicScreen));

        NthCardRewardFilterScreen nthCardRewardFilterScreen = new NthCardRewardFilterScreen(this);
        filterButtons.add(createFilterScreenButton("Choose Card Reward", (xPosition + xIncrement), yPosition,  nthCardRewardFilterScreen));

        yPosition -= yIncrement;
        ShopRelicFilterScreen shopRelicScreen = new ShopRelicFilterScreen(this);
        filterButtons.add(createFilterScreenButton("Choose Shop Relics", xPosition, yPosition, shopRelicScreen));

        yPosition -= yIncrement;
        NeowBonusFilterScreen neowBonusScreen = new NeowBonusFilterScreen(this);
        filterButtons.add(createFilterScreenButton("Choose Neow Bonuses", xPosition, yPosition, neowBonusScreen));

        yPosition -= yIncrement;
        NthRelicFilterScreen nthRelicFilterScreen = new NthRelicFilterScreen(this);
        filterButtons.add(createFilterScreenButton("Choose Relic Filter", xPosition, yPosition, nthRelicFilterScreen));

        FilterListScreen filterListScreen = new FilterListScreen();
        filterButtons.add(createFilterScreenButton("View All Filters",
                BaseFilterScreen.INFO_LEFT,
                BaseFilterScreen.INFO_TOP_CONTROLS - 150.0F,
                filterListScreen));

        baseFilterScreens.add(bossRelicScreen);
        baseFilterScreens.add(shopRelicScreen);
        baseFilterScreens.add(neowBonusScreen);
        baseFilterScreens.add(nthRelicFilterScreen);
        baseFilterScreens.add(nthCardRewardFilterScreen);
        allScreens.addAll(baseFilterScreens);
        allScreens.add(filterListScreen);
        clearButton = new ModLabeledButton("Clear All Filters", BaseFilterScreen.INFO_LEFT + 50.0F, 805.0f,
                Settings.CREAM_COLOR, Color.RED, FontHelper.tipHeaderFont, this,
                (self) -> {
                    FilterTheSpire.config.clearFilters();
                    FilterManager.clearFilters();
                    for (BaseFilterScreen screen : baseFilterScreens) {
                        screen.clearFilter();
                    }
                    clearMessage = new SpeechTextEffect((clearButton.getX() - 125.0F) * Settings.xScale, 850.0f * Settings.yScale, 2.0F, "Filters Cleared", DialogWord.AppearEffect.BUMP_IN);
                });

        neowBonusToggle = new ModLabeledToggleButton("Enable all Neow Bonuses",
                BaseFilterScreen.INFO_LEFT,         // NOTE: no scaling! (ModLabeledToggleButton scales later)
                BaseFilterScreen.INFO_BOTTOM_CHECK, // same as above
                Settings.CREAM_COLOR,
                FontHelper.charDescFont,
                FilterTheSpire.config.getBooleanKeyOrSetDefault(FilterTheSpire.config.allNeowBonusesKey, true),
                null,
                (modLabel) -> {
                },
                (button) -> FilterTheSpire.config.setBooleanKey(FilterTheSpire.config.allNeowBonusesKey, button.enabled)) {
            // Override the update of the toggle button to add an informational tool tip when hovered
            @Override
            public void update() {
                super.update();

                // Unfortunately, the hb is private, so we have to use reflection here
                Hitbox hb = ReflectionHacks.getPrivate(toggle, ModToggleButton.class, "hb");

                if (hb != null && hb.hovered) {
                    TipHelper.renderGenericTip(
                            BaseFilterScreen.INFO_LEFT * Settings.xScale,
                            (BaseFilterScreen.INFO_BOTTOM_CHECK - 40.0f) * Settings.yScale,
                            "Info",
                            "If checked, you will be guaranteed to see all four Neow options regardless of " +
                                    "whether or not the previous run made it to the act one boss. NL NL Disabling this " +
                                    "patch makes the experience more like the base game, but you may not have access " +
                                    "to the boss swap option.");
                }
            }
        };

        String[] threadCounts = IntStream.range(2, 9).mapToObj(String::valueOf).toArray(String[]::new);
        threadCountDropdown = new DropdownMenu(this, threadCounts, FontHelper.cardDescFont_N, Settings.CREAM_COLOR) {
            // Override the update of the toggle button to add an informational tool tip when hovered
            public void render(SpriteBatch sb, float x, float y) {
                super.render(sb, x, y * Settings.yScale);
                DropdownMenuPatch.renderTip(this, x, y, "Info",
                        "A higher number will search for seeds faster but will be more CPU intensive. " +
                        "The default is 2, none of these will make the game crash, but may affect background processes.");
            }
        };
        int index = ArrayUtils.indexOf(threadCounts, String.valueOf(FilterTheSpire.config.getIntKeyOrSetDefault(FilterTheSpire.config.threadCountKey, 2)));
        threadCountDropdown.setSelectedIndex(index);
    }

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
    }

    @Override
    public void render(SpriteBatch sb) {
        renderBg(sb);
        for (ModLabeledButton button : filterButtons) {
            button.render(sb);
        }

        boolean isShowingScreen = false;
        for (BaseScreen screen : allScreens) {
            if (screen.isShowing) {
                screen.render(sb);
                isShowingScreen = true;
                break;
            }
        }
        if (!isShowingScreen) {
            neowBonusToggle.render(sb);
            clearButton.render(sb);

            FontHelper.renderSmartText(sb,
                    FontHelper.tipBodyFont,
                    "This mod assumes vanilla runs and that all cards and relics have been unlocked by leveling " +
                            "each character. If you have not unlocked everything or are doing Custom Mode, the " +
                            "filters may not work consistently.",
                    BaseFilterScreen.INFO_LEFT * Settings.xScale,
                    BaseFilterScreen.INFO_TOP_MAIN * Settings.yScale,
                    BaseFilterScreen.INFO_WIDTH * Settings.xScale,
                    30.0f * Settings.yScale,
                    Settings.CREAM_COLOR);

            float threadDropdownY = BaseFilterScreen.INFO_TOP_CONTROLS - 20.0F;
            FontHelper.renderSmartText(sb,
                    FontHelper.tipBodyFont,
                    "Search thread count:",
                    BaseFilterScreen.INFO_LEFT * Settings.xScale,
                    threadDropdownY * Settings.yScale,
                    BaseFilterScreen.INFO_WIDTH * Settings.xScale,
                    30.0f * Settings.yScale,
                    Settings.CREAM_COLOR);

            threadCountDropdown.render(sb,
                    (BaseFilterScreen.INFO_LEFT + 210.0F) * Settings.xScale,
                    threadDropdownY);
            if (clearMessage != null && !clearMessage.isDone) {
                clearMessage.render(sb);
            }
        }
    }

    @Override
    public void update() {
        boolean isShowingFilterScreen = false;
        for (BaseScreen screen : allScreens) {
            if (screen.isShowing) {
                screen.update();
                isShowingFilterScreen = true;
                break;
            }
        }
        if (!isShowingFilterScreen) {
            neowBonusToggle.update();
            clearButton.update();
            threadCountDropdown.update();
            for (ModLabeledButton button : filterButtons) {
                button.update();
            }
            if (clearMessage != null && !clearMessage.isDone) {
                clearMessage.update();
            }
        }

        if (InputHelper.pressedEscape) {
            BaseMod.modSettingsUp = false;
            InputHelper.pressedEscape = false;
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
        } else if (!isUp && visible) {
            visible = false;
        }
    }

    private ModLabeledButton createFilterScreenButton(String buttonText, float xPosition, float yPosition, BaseScreen screen) {
        return new ModLabeledButton(buttonText, xPosition, yPosition,
                Settings.CREAM_COLOR, Color.GOLD, FontHelper.tipHeaderFont, this,
                (self) -> screen.open());
    }

    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s) {
        if (dropdownMenu == threadCountDropdown) {
            FilterTheSpire.config.setIntKey(FilterTheSpire.config.threadCountKey, Integer.parseInt(s));
        }
    }
}
