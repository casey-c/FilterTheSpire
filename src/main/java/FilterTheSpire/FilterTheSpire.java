package FilterTheSpire;

import FilterTheSpire.multithreading.SeedSearcher;
import FilterTheSpire.utils.*;
import FilterTheSpire.utils.Config;
import FilterTheSpire.utils.ExtraColors;
import FilterTheSpire.utils.ExtraFonts;
import basemod.BaseMod;
import basemod.ModLabeledButton;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;

@SpireInitializer
public class FilterTheSpire implements PostInitializeSubscriber, PostDungeonInitializeSubscriber, RenderSubscriber, PostUpdateSubscriber {
    private static final String version = "0.1.7";
    public static void initialize() { new FilterTheSpire(); }

    // Used by the patches to not double up VFX and SFX
    public static boolean SEARCHING_FOR_SEEDS;
    public static Config config;

    private static Texture BG;

    private ModLabeledButton stopButton;

    public FilterTheSpire() {
        BaseMod.subscribe(this);
    }

    @Override
    public void receivePostInitialize() {
        // Textures can't be loaded until the post init or it crashes
        BG = new Texture("FilterTheSpire/images/fts_background.png");

        stopButton = new ModLabeledButton("Stop Searching",
                Settings.WIDTH / 10.0f,
                150 * Settings.yScale,
                Settings.CREAM_COLOR,
                Color.GOLD,
                FontHelper.tipHeaderFont,
                null,
                (button) -> {
                    if (AbstractDungeon.player != null){
                        CardCrawlGame.startOver();
                        Settings.isTrial = false;
                        Settings.isDailyRun = false;
                        Settings.isEndless = false;
                        CardCrawlGame.trial = null;
                        SaveAndContinue.deleteSave(AbstractDungeon.player);
                    }

                    if (SEARCHING_FOR_SEEDS){
                        SEARCHING_FOR_SEEDS = false;
                        firstTimeThrough = true;
                        searcherActive = false;
                    }
        });

        config = new Config();
        Config.setupConfigMenu();
    }

    // --------------------------------------------------------------------------------

    private boolean firstTimeThrough = true;
    private boolean searcherActive = false;

    @Override
    public void receivePostDungeonInitialize() {
        if (!FilterManager.hasFilters()) {
            // Nothing to do (no need for refreshing)
            return;
        }

        if (firstTimeThrough && !Settings.seedSet) {
            CardPoolHelper.resetCharacterCardPoolsForSettings();
            FilterManager.sortFilters();
            SEARCHING_FOR_SEEDS = true;
            firstTimeThrough = false;

            SeedSearcher.searchForSeed();
            searcherActive = true;
        }
        else {
            SEARCHING_FOR_SEEDS = false;
            firstTimeThrough = true;
        }
    }


    // --------------------------------------------------------------------------------

    // All these fields help with making a nice smooth fadeout
    private boolean currentlyFading = false;
    private float fadeTime;
    private static final float maxFadeTime = 2.0f;

    private Color blackColor = Color.BLACK.cpy();
    private Color whiteColor = Color.WHITE.cpy();
    private Color pinkTextColor = ExtraColors.PINK_COLOR.cpy();

    private Color creamTextColor = Settings.CREAM_COLOR.cpy();
    private Color grayTextColor = Color.GRAY.cpy();

    private String totalSearched = "";

    void setColorOpacities(float val) {
        blackColor.a = val;
        whiteColor.a = val;
        pinkTextColor.a = val;
        grayTextColor.a = val;
        creamTextColor.a = val;
    }

    @Override
    public void receiveRender(SpriteBatch sb) {
        if (searcherActive) {
            totalSearched = SeedSearcher.getNumSeedsExamined();

            // Active and finished - start fading the loading screen and cancel the active status
            if (SeedSearcher.isFinished()) {
                searcherActive = false;
                currentlyFading = true;
                fadeTime = maxFadeTime;

                SeedSearcher.makeSeedReal();
            }
        }
        // Not active, and currently fading out the loading screen
        else if (currentlyFading){
            fadeTime -= Gdx.graphics.getDeltaTime();

            // Ending criteria
            if (fadeTime < 0.0f) {
                currentlyFading = false;
                System.out.println("Total seeds searched: " + totalSearched);
                totalSearched = "";
                setColorOpacities(1.0f);
            }
            else {
                if (fadeTime < 1.0f) setColorOpacities(fadeTime);
                else setColorOpacities(1.0f);
            }
        }

        // --------------------------------------------------------------------------------
        // The actual draw calls occur now
        // --------------------------------------------------------------------------------

        if (searcherActive || currentlyFading) {
            // BLACK
            sb.setColor(blackColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);

            // IMAGE
            sb.setColor(whiteColor);
            sb.draw(BG, 0, 0, Settings.WIDTH, Settings.HEIGHT);

            // Main Numbers
            FontHelper.renderFontCentered(sb, ExtraFonts.largeNumberFont(), totalSearched, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, pinkTextColor);

            // Extra Info
            FontHelper.renderFontCentered(sb,
                    FontHelper.menuBannerFont,
                    "Searching for the perfect seed...",
                    (Settings.WIDTH * 0.5f),
                    (Settings.HEIGHT * 0.5f) + (224.0f * Settings.yScale),
                    creamTextColor
            );

            FontHelper.renderFontCentered(sb,
                    FontHelper.menuBannerFont,
                    "Seeds Explored",
                    (Settings.WIDTH * 0.5f),
                    321 * Settings.yScale,
                    grayTextColor
            );

            FontHelper.renderFontRightTopAligned(sb,
                    FontHelper.menuBannerFont,
                    "Filter the Spire",
                    Settings.WIDTH - (85.0f * Settings.xScale),
                    945 * Settings.yScale,
                    grayTextColor
            );

            FontHelper.renderFontRightTopAligned(sb,
                    FontHelper.menuBannerFont,
                    version,
                    Settings.WIDTH - (85.0f * Settings.xScale),
                    890 * Settings.yScale,
                    grayTextColor
            );

            // buttons
            if (!currentlyFading){
                stopButton.render(sb);
            }
        }
    }

    @Override
    public void receivePostUpdate() {
        if (searcherActive){
            stopButton.update();
        }
    }
}
