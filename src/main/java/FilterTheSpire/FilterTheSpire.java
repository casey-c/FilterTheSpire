package FilterTheSpire;

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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BooleanSupplier;

@SpireInitializer
public class FilterTheSpire implements PostInitializeSubscriber, PostDungeonInitializeSubscriber, RenderSubscriber {
    public static void initialize() { new FilterTheSpire(); }

    private int timesStartedOver = 0;
    private final int MAX_START_OVER = 200;

    //private boolean isResetting = false;
    public static boolean SEARCHING_FOR_SEEDS;

    // TODO: localization
    private static final String info = "Searching for a suitable seed";
    private static final String extra_info = "Seeds searched: ";

    //private static Texture BG = new Texture("FilterTheSpire/images/fts_background.png");
    private static Texture BG;

    private ArrayList<BooleanSupplier> validators = new ArrayList<>();

    public FilterTheSpire() {
        BaseMod.subscribe(this);

        // Example usage - adding filters is as easy as passing a function that returns a bool
        //   all predicates in the validators list are logical AND together at the end
        //   logical OR will be more complicated to make it generic, but the idea is the same
        //   (pass a function that returns the result of the OR condition)
        validators.add(() -> bossSwapIs("Pandora's Box"));
        //validators.add(() -> bossSwapIs("Snecko Eye"));
    }

    // DEBUG
    private void printRelicPool() {
        if (CardCrawlGame.isInARun() && CardCrawlGame.chosenCharacter != null) {
            ArrayList<String> bossRelics = new ArrayList(AbstractDungeon.bossRelicPool);
            bossRelics.sort(String::compareTo);

            System.out.println("\n---------------------------------------");
            System.out.println("\nBoss relics (" + CardCrawlGame.chosenCharacter.name() + "):");
            bossRelics.forEach(System.out::println);
            System.out.println("---------------------------------------\n");
        }
    }

    private boolean validateSeed() {
        return validators.stream().allMatch(BooleanSupplier::getAsBoolean);
    }

    // Simulates using the seed to sort the boss swap list
    private boolean bossSwapIs(String targetRelic) {
        Random relicRng = new Random(Settings.seed);

        // Skip past all these
        relicRng.randomLong(); // common
        relicRng.randomLong(); // uncommon
        relicRng.randomLong(); // rare
        relicRng.randomLong(); // shop
        //relicRng.randomLong(); // boss <- this is the one needed (we perform it below)

        ArrayList<String> bossRelicPool = new ArrayList<>();
        RelicLibrary.populateRelicPool(bossRelicPool, AbstractRelic.RelicTier.BOSS, AbstractDungeon.player.chosenClass);
        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));

        return !bossRelicPool.isEmpty() && bossRelicPool.get(0) == targetRelic;
    }


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

    private boolean ignoreInit = false;

    @Override
    public void receivePostDungeonInitialize() {
        // This func is called on our makeReal() - but we know its a valid seed already
        if (ignoreInit) {
            // Reset
            ignoreInit = false;
            timesStartedOver = 0;
            SEARCHING_FOR_SEEDS = false;

            return;
        }

        while (!validateSeed()) {
            SEARCHING_FOR_SEEDS = true;
            RestartHelper.randomSeed();
            timesStartedOver++;
        }

        System.out.println("Found a valid start in " + timesStartedOver + " attempts.");

        // If we required at least one reset
        if (timesStartedOver > 0) {
            RestartHelper.makeReal();
            ignoreInit = true;
            // Everything will be reset on our second pass through this function
        }
        else {
            // Reset
            ignoreInit = false;
            timesStartedOver = 0;
            SEARCHING_FOR_SEEDS = false;
        }


    }


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
                    "v0.1.1",
                    Settings.WIDTH - (85.0f * Settings.scale),
                    890 * Settings.scale,
                    Color.GRAY
            );
        }
    }

    @Override
    public void receivePostInitialize() {
        BG = new Texture("FilterTheSpire/images/fts_background.png");
    }
}
