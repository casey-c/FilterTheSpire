package SlayTheSeeds.patches;

import SlayTheSeeds.BossSwapSeed;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.neow.NeowEvent;

public class NeowEventPatches {
    @SpirePatch(
            clz = NeowEvent.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    boolean.class
            }
    )
    public static class GuaranteeAllRewardsPatch {
        public static void Postfix(NeowEvent __instance, boolean isDone) {
            int x = (int)ReflectionHacks.getPrivate(__instance, NeowEvent.class, "bossCount");
            System.out.println("bossCount is: " + x);

            if (x == 0) ReflectionHacks.setPrivate(__instance, NeowEvent.class, "bossCount", 1);

        }
    }

    @SpirePatch(
            clz = NeowEvent.class,
            method = "talk",
            paramtypez = {String.class}
    )
    public static class NonOverlappingBubblesPatch {
        public static SpireReturn Prefix(NeowEvent __instance) {
            return (BossSwapSeed.SEARCHING_FOR_SEEDS) ? SpireReturn.Return(null) : SpireReturn.Continue();
        }

    }
}
