package SlayTheSeeds;

import basemod.BaseMod;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

@SpireInitializer
public class BossSwapSeed implements PostDungeonInitializeSubscriber, RenderSubscriber {
    public static void initialize() { new BossSwapSeed(); }

    private int timesStartedOver = 0;
    private final int MAX_START_OVER = 200;

    private boolean isResetting = false;
    public static boolean SEARCHING_FOR_SEEDS;

    // TODO: localization
    private static final String info = "Searching for a suitable seed";
    private static final String extra_info = "Seeds searched: ";

    private ArrayList<BooleanSupplier> validators = new ArrayList<>();

    public BossSwapSeed() {
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

    // TODO: other filters
    private boolean bossSwapIs(String targetRelic) {
        if (CardCrawlGame.isInARun()) {
            ArrayList<String> bossRelics = AbstractDungeon.bossRelicPool;

            // TODO: remove / debug
//            printRelicPool();

            if (!bossRelics.isEmpty()) {
                String relic = bossRelics.get(0);
                return targetRelic == relic;
            }
        }

        return false;
    }

    private boolean validateSeed() {
        return validators.stream().allMatch(BooleanSupplier::getAsBoolean);
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

    @Override
    public void receivePostDungeonInitialize() {
        // Mute when first starting the search
        if (timesStartedOver == 0) {
            SEARCHING_FOR_SEEDS = true;
        }

        if (validateSeed()) {
            timesStartedOver = 0;
            isResetting = false;

            SEARCHING_FOR_SEEDS = false;
            if (AbstractDungeon.scene != null) {
                // Clear and restart the ambiance?
                AbstractDungeon.scene.fadeOutAmbiance();
                CardCrawlGame.sound.playAndLoop("AMBIANCE_BOTTOM");

                // Play the Neow sound we originally patched out
                playNeowSound();
            }
        }
        else {
            // Haven't reached the reset limit yet, so can reset and try again
            if (timesStartedOver < MAX_START_OVER) {
                isResetting = true;
                RestartHelper.restartRun();
                timesStartedOver++;
            }
            else {
                isResetting = false;
                System.out.println("ERROR: ran out of resets"); // TODO: show a warning message on neow
            }
        }

    }


    @Override
    public void receiveRender(SpriteBatch sb) {
        if (isResetting) {
            sb.setColor(Color.BLACK);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG,
                    0,
                    0,
                    Settings.WIDTH,
                    Settings.HEIGHT);

            // Render the loading text
            FontHelper.renderFontCentered(sb,
                    FontHelper.dungeonTitleFont,
                    info,
                    Settings.WIDTH / 2.0f,
                    Settings.HEIGHT / 2.0f);

            FontHelper.renderFontLeftDownAligned(sb,
                    FontHelper.eventBodyText,
                    extra_info + timesStartedOver,
                    100 * Settings.scale,
                    100 * Settings.scale,
                    Settings.CREAM_COLOR);
        }
    }
}
