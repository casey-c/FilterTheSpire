package SlayTheSeeds.patches;

import SlayTheSeeds.BossSwapSeed;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class SoundMasterPatch {
//    @SpirePatch(
//            clz = SoundMaster.class,
//            method = "play",
//            paramtypez = {String.class, boolean.class}
//    )
//    public static class PlayStringBoolPatch {
//        public static void Prefix(SoundMaster __instance, String _key, boolean _useBgmVolume) {
//            System.out.println("OJB: sound master patch is playing: " + _key + " | PLAY STRING / BOOL useBgmVol");
//        }
//    }

//    // Yes: Neow audio
//    @SpirePatch(
//            clz = SoundMaster.class,
//            method = "play",
//            paramtypez = {String.class}
//    )
//    public static class PlayStringPatch {
//        public static void Prefix(SoundMaster __instance, String _key) {
//            System.out.println("OJB: sound master patch is playing: " + _key + " | PLAY STRING");
//            if (BossSwapSeed.SEARCHING_FOR_SEEDS) {
//                System.out.println("SKIPPING!");
//                return;
//            }
//        }
//    }

    @SpirePatch(
            clz = NeowEvent.class,
            method = "playSfx"
    )
    public static class NeowSoundPatch {
        public static SpireReturn Prefix(NeowEvent __instance) {
            System.out.println("NeowEvent Screen playing sound");
            System.out.println("************************************\n\n");

            if (BossSwapSeed.SEARCHING_FOR_SEEDS) {
                System.out.println("searching for seeds true, so skipping the rest?");
                return SpireReturn.Return(null);
            }
            else
                return SpireReturn.Continue();
        }
    }

    // YES (Ambiance bottom)
    @SpirePatch(
            clz = SoundMaster.class,
            method = "playAndLoop",
            paramtypez = {String.class}
    )
    public static class PlayAndLoopStringPatch {
        public static SpireReturn<Long> Prefix(SoundMaster __instance, String _key) {
            if (BossSwapSeed.SEARCHING_FOR_SEEDS)
                return SpireReturn.Return(0L);
            else
                return SpireReturn.Continue();
            //System.out.println("OJB: sound master patch is playing: " + _key + " | PLAY AND LOOP: STRING");
        }
    }


    @SpirePatch(
            clz = MusicMaster.class,
            method = "changeBGM",
            paramtypez = {String.class}
    )
    public static class MusicMasterPatch {
        public static SpireReturn Prefix(MusicMaster __instance, String _key) {
            if (BossSwapSeed.SEARCHING_FOR_SEEDS)
                return SpireReturn.Return(null);
            else
                return SpireReturn.Continue();
        }
    }

    //public void changeBGM(String key) {


//    @SpirePatch(
//            clz = SoundMaster.class,
//            method = "play",
//            paramtypez = {String.class, float.class}
//    )
//    public static class PlayStringFloatPatch {
//        public static void Prefix(SoundMaster __instance, String _key, float __pitchVariation) {
//            System.out.println("OJB: sound master patch is playing: " + _key + " | PLAY STRING / FLOAT pitchVariation");
//        }
//    }
//
//    @SpirePatch(
//            clz = SoundMaster.class,
//            method = "playA"
//    )
//    public static class PlayAPatch {
//        public static void Prefix(SoundMaster __instance, String _key, float __pitchVariation) {
//            System.out.println("OJB: sound master patch is playing: " + _key + " | PLAYA: STRING / FLOAT pitchVariation");
//        }
//    }
//
//    @SpirePatch(
//            clz = SoundMaster.class,
//            method = "playV"
//    )
//    public static class PlayVPatch {
//        public static void Prefix(SoundMaster __instance, String _key, float __volumeMod) {
//            System.out.println("OJB: sound master patch is playing: " + _key + " | PLAYV: STRING / FLOAT volumeMod");
//        }
//    }

//    @SpirePatch(
//            clz = SoundMaster.class,
//            method = "playAV"
//    )
//    public static class PlayAVPatch {
//        public static void Prefix(SoundMaster __instance, String _key, float _pitchAdjust, float __volumeMod) {
//            System.out.println("OJB: sound master patch is playing: " + _key + " | PLAYV: STRING / FLOAT / FLOAT pitch, volumeMod");
//        }
//    }


//    @SpirePatch(
//            clz = SoundMaster.class,
//            method = "playAndLoop",
//            paramtypez = {String.class, float.class}
//    )
//    public static class PlayAndLoopStringFloatPatch {
//        public static void Prefix(SoundMaster __instance, String _key, float volume) {
//            System.out.println("OJB: sound master patch is playing: " + _key + " | PLAY AND LOOP: STRING / FLOAT volume");
//        }
//    }

    /*
    TODO: only play(String) and playAndLoop(String) need fixes for my purposes

    play(String, bool)
    play(String)
    play(String,float)
    playA(String,float)
    playV(String,float)
    playAV(String,float,float)
    playAndLoop(String)
    playAndLoop(String,float)
     */

}
