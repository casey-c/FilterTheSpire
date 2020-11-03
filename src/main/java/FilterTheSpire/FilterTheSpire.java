package FilterTheSpire;

import FilterTheSpire.utils.Config;
import FilterTheSpire.utils.ExtraColors;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.SeedTesting;
import basemod.BaseMod;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BagOfMarbles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.BooleanSupplier;

@SpireInitializer
public class FilterTheSpire implements PostInitializeSubscriber, PostDungeonInitializeSubscriber, RenderSubscriber {
    public static void initialize() { new FilterTheSpire(); }

    private int timesStartedOver = 0;
    private int MAX_SEEDS = 300;

    public static boolean SEARCHING_FOR_SEEDS;
    private boolean ignoreInit = false;

    private static Texture BG;

    public FilterTheSpire() {
        BaseMod.subscribe(this);
    }

    @Override
    public void receivePostInitialize() {
        // Textures can't be loaded until the post init or it crashes
        BG = new Texture("FilterTheSpire/images/fts_background.png");

        Config.setupConfigMenu();
    }

    // --------------------------------------------------------------------------------

    private void playNeowSound() {
        int roll = MathUtils.random(3);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_NEOW_1A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_NEOW_1B");
        } else if (roll == 2) {
            CardCrawlGame.sound.play("VO_NEOW_2A");
        } else {
            CardCrawlGame.sound.play("VO_NEOW_2B");
        }
    }

    // --------------------------------------------------------------------------------

    /*
        This is our main seed searching loop; it is called when first entering a dungeon and proceeds to use the
        FilterManager and RestartHelper in tandem to check through random seeds. Once it finds one that passes the
        validateFilters() test (i.e. passes all the filters set by the player) it will make that seed into the current
        run.

        This callback is called twice: once when clicking the menu option to start the run, and once after the
        call to RestartHelper.makeReal() after we've found a suitable seed.
     */
    @Override
    public void receivePostDungeonInitialize() {
        // Second time through all we do is reset
        if (ignoreInit) {
            // Reset
            ignoreInit = false;
            timesStartedOver = 0;
            SEARCHING_FOR_SEEDS = false;

            return;
        }


        while (!FilterManager.validateFilters()) {
            SEARCHING_FOR_SEEDS = true;
            RestartHelper.randomSeed();
            timesStartedOver++;

            if (timesStartedOver > MAX_SEEDS) {
                System.out.println("FILTER THE SPIRE WARNING: Exceeded max number of seeds to search (" + MAX_SEEDS + "). Results may not match filters.");
                break;
            }
        }

        System.out.println("Found a valid start in " + timesStartedOver + " attempts.");

        // If we required at least one reset
        if (timesStartedOver > 0) {
            // This essentially puts us back at the start of this function with a "real" run
            RestartHelper.makeReal();
            ignoreInit = true;
        }
        else {
            // Found the desired seed on the first try, so do the reset stuff we would usually do when entering the
            //   second time
            ignoreInit = false;
            timesStartedOver = 0;
            SEARCHING_FOR_SEEDS = false;
        }
    }

    // --------------------------------------------------------------------------------


    @Override
    public void receiveRender(SpriteBatch sb) {
        if (SEARCHING_FOR_SEEDS) {

            if (BG != null) {
                sb.setColor(Color.WHITE);
                sb.draw(BG, 0, 0, Settings.WIDTH, Settings.HEIGHT);
            }
            else {
                System.out.println("OJB WARNING: BG texture not initialized properly");
            }

            // TODO: localization

            FontHelper.renderFontCentered(sb,
                    FontHelper.menuBannerFont,
                    "Searching for the perfect seed...",
                    (Settings.WIDTH * 0.5f),
                    (Settings.HEIGHT * 0.5f) + (224.0f * Settings.scale),
                    Settings.CREAM_COLOR
                    );

            FontHelper.renderFontCentered(sb,
                    //FontHelper.tipBodyFont,
                    ExtraFonts.largeNumberFont(),
                    "" + timesStartedOver,
                    (Settings.WIDTH * 0.5f),
                    (Settings.HEIGHT * 0.5f),
                    ExtraColors.PINK_COLOR
            );

            FontHelper.renderFontCentered(sb,
                    FontHelper.menuBannerFont,
                    "Seeds Explored",
                    (Settings.WIDTH * 0.5f),
                    321 * Settings.scale,
                    Color.GRAY
            );

            FontHelper.renderFontRightTopAligned(sb,
                    FontHelper.menuBannerFont,
                    "Filter the Spire",
                    Settings.WIDTH - (85.0f * Settings.scale),
                    945 * Settings.scale,
                    Color.GRAY
            );

            FontHelper.renderFontRightTopAligned(sb,
                    FontHelper.menuBannerFont,
                    "v0.1.2",
                    Settings.WIDTH - (85.0f * Settings.scale),
                    890 * Settings.scale,
                    Color.GRAY
            );
        }
    }

}
