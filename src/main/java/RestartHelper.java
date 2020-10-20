import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen;
import com.megacrit.cardcrawl.shop.ShopScreen;

public class RestartHelper {
        public static void restartRun() {
            CardCrawlGame.music.fadeAll();

            if (CardCrawlGame.isInARun()) {
                if (AbstractDungeon.getCurrMapNode() != null) {
                    AbstractRoom room = AbstractDungeon.getCurrRoom();
                    if (room != null) {
                        room.clearEvent();
                    }

                }

                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.reset();
            }


            //AbstractDungeon.dungeonMapScreen.closeInstantly();
            CardCrawlGame.dungeonTransitionScreen = new DungeonTransitionScreen(Exordium.ID);
            //AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;

            Settings.hasEmeraldKey = false;
            Settings.hasRubyKey = false;
            Settings.hasSapphireKey = false;

            ShopScreen.resetPurgeCost();
            CardCrawlGame.tips.initialize();
            CardCrawlGame.metricData.clearData();
            CardHelper.clear();
            TipTracker.refresh();
            System.gc();

            if (CardCrawlGame.chosenCharacter == null) {
                CardCrawlGame.chosenCharacter = AbstractDungeon.player.chosenClass;
            }

            if (!Settings.seedSet) {
                Long sTime = System.nanoTime();
                Random rng = new Random(sTime);
                Settings.seedSourceTimestamp = sTime;
                Settings.seed = SeedHelper.generateUnoffensiveSeed(rng);
                SeedHelper.cachedSeed = null;
            }

            AbstractDungeon.generateSeeds();
            CardCrawlGame.mode = CardCrawlGame.GameMode.CHAR_SELECT;
        }
}
