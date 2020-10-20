import basemod.BaseMod;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.StartGameSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Arrays;

@SpireInitializer
public class BossSwapSeed implements PostInitializeSubscriber, StartGameSubscriber, PostDungeonInitializeSubscriber {
    public static void initialize() { new BossSwapSeed(); }

    private int timesStartedOver = 0;
    private final int MAX_START_OVER = 100;

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
                    //System.out.println("pandora's found!");
                }
                else {
                    System.out.println("not pandora -- resetting");
                    if (timesStartedOver < MAX_START_OVER) {
                        RestartHelper.restartRun();
                        timesStartedOver++;
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
}
