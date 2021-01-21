package FilterTheSpire.patches;

import FilterTheSpire.FilterTheSpire;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.neow.NeowEvent;

public class CustomModePatch {
    @SpirePatch(
            clz= NeowEvent.class,
            method = "shouldSkipNeowDialog"
    )
    public static class CustomModeShowNeow {
        public static SpireReturn<Boolean> Prefix(NeowEvent __instance) {
            return SpireReturn.Return(false);
            //return (FilterTheSpire.SEARCHING_FOR_SEEDS) ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }
}
