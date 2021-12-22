package FilterTheSpire;

import FilterTheSpire.filters.BlessingFilter;
import FilterTheSpire.filters.NthColorlessRareCardFilter;
import FilterTheSpire.filters.PandorasCardFilter;
import FilterTheSpire.multithreading.SeedSearcher;
import FilterTheSpire.simulators.CardTransformSimulator;
import FilterTheSpire.utils.Config;
import FilterTheSpire.utils.ExtraColors;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.SeedTesting;
import basemod.BaseMod;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.neow.NeowReward;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Filter;

@SpireInitializer
public class FilterTheSpire implements PostInitializeSubscriber, PostDungeonInitializeSubscriber, RenderSubscriber {
    private static final String version = "0.1.5";
    public static void initialize() { new FilterTheSpire(); }

    // Used by the patches to not double up VFX and SFX
    public static boolean SEARCHING_FOR_SEEDS;
    public static Config config;

    private static Texture BG;

    public FilterTheSpire() {
        BaseMod.subscribe(this);
    }

    @Override
    public void receivePostInitialize() {
        // Textures can't be loaded until the post init or it crashes
        BG = new Texture("FilterTheSpire/images/fts_background.png");

        config = new Config();
        Config.setupConfigMenu();

//        FilterManager.setFirstBossIs("Slime Boss");
//        FilterManager.setFirstEliteIs("3 Sentries");
//        FilterManager.setFirstCombatIs("2 Louse");
//        FilterManager.setValidatorFromString("colorlessRareIs", new NthColorlessRareCardFilter(Collections.singletonList("Apotheosis"), 1));

//        FilterManager.setBossSwapIs("Pandora's Box");

        // for testing, try different rarities
//        ArrayList<String> relicsToSearch = new ArrayList<>();
//        relicsToSearch.add("Dead Branch");
//        relicsToSearch.add("Toy Ornithopter");
//        relicsToSearch.add("Shuriken");
//        FilterManager.setNthRelicFromValidList(relicsToSearch);
    }

    // --------------------------------------------------------------------------------

    private boolean firstTimeThrough = true;
    private boolean searcherActive = false;

    @Override
    public void receivePostDungeonInitialize() {
//        HashMap<String, Integer> cards = new HashMap<>();
//        cards.put("Blade Dance", 4);
//        cards.put("Accuracy", 1);
//        FilterManager.setPandorasCardFilter(cards);
//        FilterManager.setValidatorFromString("blessingFilter", new BlessingFilter(NeowReward.NeowRewardType.ONE_RARE_RELIC));
//        FilterManager.setValidatorFromString("blessingFilter", new BlessingFilter(NeowReward.NeowRewardType.TRANSFORM_CARD, "Prepared"));
//        FilterManager.setValidatorFromString("blessingFilter", new BlessingFilter(NeowReward.NeowRewardType.ONE_RANDOM_RARE_CARD, "Glass Knife"));

        if (!FilterManager.hasFilters()) {
            // Nothing to do (no need for refreshing)

            // TODO: debug remove
//            SeedTesting.bossTest();

            return;
        }

        if (firstTimeThrough && Settings.isStandardRun()) {
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
                    (Settings.HEIGHT * 0.5f) + (224.0f * Settings.scale),
                    creamTextColor
            );

            FontHelper.renderFontCentered(sb,
                    FontHelper.menuBannerFont,
                    "Seeds Explored",
                    (Settings.WIDTH * 0.5f),
                    321 * Settings.scale,
                    grayTextColor
            );

            FontHelper.renderFontRightTopAligned(sb,
                    FontHelper.menuBannerFont,
                    "Filter the Spire",
                    Settings.WIDTH - (85.0f * Settings.scale),
                    945 * Settings.scale,
                    grayTextColor
            );

            FontHelper.renderFontRightTopAligned(sb,
                    FontHelper.menuBannerFont,
                    version,
                    Settings.WIDTH - (85.0f * Settings.scale),
                    890 * Settings.scale,
                    grayTextColor
            );

        }
    }
}
