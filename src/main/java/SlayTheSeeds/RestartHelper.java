package SlayTheSeeds;

import com.megacrit.cardcrawl.audio.SoundMaster;
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
            // TODO
            //CardCrawlGame.music.fadeAll();

            // Temporarily mute

//            boolean MUTE_IF_BG_ORIGINAL = CardCrawlGame.MUTE_IF_BG;
//            CardCrawlGame.MUTE_IF_BG = true;
//
//            boolean isBackgroundedOriginal = Settings.isBackgrounded;
//            Settings.isBackgrounded = true;
//
//            float MASTER_VOLUME_ORIGINAL = Settings.MASTER_VOLUME;
//            float MUSIC_VOLUME_ORIGINAL = Settings.MUSIC_VOLUME;
//            float SOUND_VOLUME_ORIGINAL = Settings.SOUND_VOLUME;
//            boolean AMBIANCE_ON_ORIGINAL = Settings.AMBIANCE_ON;
//
//            Settings.MASTER_VOLUME = 0;
//            Settings.MUSIC_VOLUME = 0;
//            Settings.SOUND_VOLUME = 0;
//            Settings.AMBIANCE_ON = false;





            if (CardCrawlGame.isInARun()) {
//                // Bugfix sound?
//                if (AbstractDungeon.scene != null) {
//                    AbstractDungeon.scene.muteAmbienceVolume();
//                }

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

            // Revert mute
//            CardCrawlGame.MUTE_IF_BG = MUTE_IF_BG_ORIGINAL;
//            Settings.isBackgrounded = isBackgroundedOriginal;
//
//            Settings.MASTER_VOLUME = MASTER_VOLUME_ORIGINAL;
//            Settings.MUSIC_VOLUME = MUSIC_VOLUME_ORIGINAL;
//            Settings.SOUND_VOLUME = SOUND_VOLUME_ORIGINAL;
//            Settings.AMBIANCE_ON = AMBIANCE_ON_ORIGINAL;
        }
}
