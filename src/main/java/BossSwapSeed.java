import basemod.BaseMod;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    // TODO: localization
    private static final String info = "Searching for a suitable seed";
    private static final String extra_info = "Seeds searched: ";

    private ArrayList<BooleanSupplier> validators = new ArrayList<>();

    public BossSwapSeed() {
        BaseMod.subscribe(this);

        //validators.add(() -> bossSwapIs("Pandora's Box"));
        validators.add(() -> bossSwapIs("Snecko Eye"));
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

    private boolean bossSwapIs(String targetRelic) {
        if (CardCrawlGame.isInARun()) {
            ArrayList<String> bossRelics = AbstractDungeon.bossRelicPool;

            // TODO: remove / debug
            printRelicPool();

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

    @Override
    public void receivePostDungeonInitialize() {
        if (validateSeed()) {
            timesStartedOver = 0;
            isResetting = false;
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
