package patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.neow.NeowEvent;

@SpirePatch(
        clz = NeowEvent.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                boolean.class
        }
)
public class NeowEventPatch {
        public static void Postfix(NeowEvent __instance, boolean isDone) {
                int x = (int)ReflectionHacks.getPrivate(__instance, NeowEvent.class, "bossCount");
                System.out.println("bossCount is: " + x);

                if (x == 0) {
                        ReflectionHacks.setPrivate(__instance, NeowEvent.class, "bossCount", 1);

//                        System.out.println("After updating to 1 hopefully");
//                        x = (int)ReflectionHacks.getPrivate(__instance, NeowEvent.class, "bossCount");
//                        System.out.println("x is: " + x);
                }

        }
}
