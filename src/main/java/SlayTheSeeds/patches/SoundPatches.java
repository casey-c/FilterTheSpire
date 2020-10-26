package SlayTheSeeds.patches;

import SlayTheSeeds.BossSwapSeed;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.neow.NeowEvent;

public class SoundPatches {
     // This patch stops Neow from spamming his rumbly tummy sounds as the seeds reset
    @SpirePatch(
            clz = NeowEvent.class,
            method = "playSfx"
    )
    public static class NeowSoundPatch {
        public static SpireReturn Prefix(NeowEvent __instance) {
            return (BossSwapSeed.SEARCHING_FOR_SEEDS) ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }

    // This patch stops the ambient sound from repeatedly starting with each reset
    @SpirePatch(
            clz = SoundMaster.class,
            method = "playAndLoop",
            paramtypez = {String.class}
    )
    public static class PlayAndLoopStringPatch {
        public static SpireReturn<Long> Prefix(SoundMaster __instance, String _key) {
            return (BossSwapSeed.SEARCHING_FOR_SEEDS) ? SpireReturn.Return(0L) : SpireReturn.Continue();
        }
    }


    // This patch stops the Exordium theme from being added to the queue over and over and playing over itself
    @SpirePatch(
            clz = MusicMaster.class,
            method = "changeBGM",
            paramtypez = {String.class}
    )
    public static class MusicMasterPatch {
        public static SpireReturn Prefix(MusicMaster __instance, String _key) {
            return (BossSwapSeed.SEARCHING_FOR_SEEDS) ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }
}
