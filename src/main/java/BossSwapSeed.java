import basemod.BaseMod;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.RenderSubscriber;
import basemod.interfaces.StartGameSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.potions.EntropicBrew;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Arrays;

@SpireInitializer
public class BossSwapSeed implements PostInitializeSubscriber, StartGameSubscriber, PostDungeonInitializeSubscriber, RenderSubscriber {
    public static void initialize() { new BossSwapSeed(); }

    private int timesStartedOver = 0;
    private final int MAX_START_OVER = 200;

    private boolean isResetting = false;

//    private void unlockAll() {
//        for (AbstractPlayer.PlayerClass c : AbstractPlayer.PlayerClass.values()) {
//            System.out.println("Attempting to unlock " + c.name());
//
//            for (int j = 0; j < 4; ++j) {
//                ArrayList<AbstractUnlock> bundle = UnlockTracker.getUnlockBundle(c, j);
////                for (AbstractUnlock u : bundle) {
////                    AbstractDungeon.gUnlockScreen.open(bundle, true);
//
//                switch (bundle.get(0).type) {
//                    case CARD: {
//                        System.out.println("bundle is cards");
//                        for (int i = 0; i < bundle.size(); ++i) {
//                            System.out.println(bundle.get(i).card.name);
//                            UnlockTracker.unlockCard(bundle.get(i).card.cardID);
//                        }
//                        break;
//                    }
//                    case RELIC: {
//                        System.out.println("bundle is relics");
//                        for (int i = 0; i < bundle.size(); ++i) {
//                            System.out.println(bundle.get(i).relic.name);
//                            UnlockTracker.hardUnlockOverride(bundle.get(i).relic.relicId);
//                            UnlockTracker.markRelicAsSeen(bundle.get(i).relic.relicId);
//                        }
//                        break;
//                    }
//                    case CHARACTER: {
//                        //bundle.get(0).onUnlockScreenOpen();
//                        break;
//                    }
//                }
//                //}
//            }
//        }
//    }

    public BossSwapSeed() {
        BaseMod.subscribe(this);



        //public static ArrayList<AbstractUnlock> getUnlockBundle(AbstractPlayer.PlayerClass c, int unlockLevel) {

    }

    @Override
    public void receivePostInitialize() {
        System.out.println("OJB: boss swap seed init");

//        boolean b = UnlockTracker.isRelicLocked("Pandora's Box");
//        System.out.println("OJB: unlock tracker has pandoras box locked: " + b);

        // TODO
//        unlockAll();
    }

    @Override
    public void receiveStartGame() {
//        timesStartedOver = 0;
//        System.out.println("reset times started over to 0");
//        System.out.println("OJB: START GAME");
//        if (CardCrawlGame.isInARun())
//            System.out.println("in run");
//        else
//            System.out.println("not in run");
//        System.out.println("----------------");
    }

    @Override
    public void receivePostDungeonInitialize() {
        //System.out.println("OJB: POST DUNGEON INIT");
        if (CardCrawlGame.isInARun()) {
            System.out.println("in run, seed: " + Settings.seed);
            ArrayList<String> bossRelics = AbstractDungeon.bossRelicPool;
            if (!bossRelics.isEmpty()) {
                System.out.println("Potential boss swap relic: " + bossRelics.get(0));

                String relic = bossRelics.get(0);
                if (relic == "Pandora's Box") {
                    System.out.println("pandora's found after " + timesStartedOver);
                    timesStartedOver = 0;

                    isResetting = false;
                }
                else {
                    System.out.println("not pandora -- resetting");
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

//                System.out.println("All relics: ");
//                System.out.print("[ ");
//                for (String s : bossRelics) {
//                    System.out.print("'" + s + "' ");
//                }
//                System.out.println(" ]");
            }

        }
//        else
//            System.out.println("not in run");
        System.out.println("----------------");
    }

    private static final String info = "Searching for a suitable seed";

    @Override
    public void receiveRender(SpriteBatch sb) {
        if (isResetting) {
            sb.setColor(Color.BLACK);
//            sb.draw(ImageMaster.WHITE_SQUARE_IMG,
//                    Settings.WIDTH / 2.0f,
//                    Settings.HEIGHT / 2.0f,
//                    100.0f * Settings.scale,
//                    100.0f * Settings.scale);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG,
                    0,
                    0,
                    Settings.WIDTH,
                    Settings.HEIGHT);

            //float x = (MathUtils.cosDeg((float) (System.currentTimeMillis() / 10L % 360L)) + 1.25F) / 2.3F;

            // Add ellipses to string
            //int amt = (int)((long)(System.currentTimeMillis() / 500.0f) % 3);

            // Render the loading text
            FontHelper.renderFontCentered(sb,
                    FontHelper.dungeonTitleFont,
                    info,
                    Settings.WIDTH / 2.0f,
                    Settings.HEIGHT / 2.0f);

            String seedsSearched = "Seeds searched: " + timesStartedOver;

            FontHelper.renderFontLeftDownAligned(sb,
                    FontHelper.eventBodyText,
                    seedsSearched,
                    100,
                    100,
                    Settings.CREAM_COLOR);
        }
    }
}
