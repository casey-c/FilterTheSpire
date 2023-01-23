package FilterTheSpire.patches;

import FilterTheSpire.FilterTheSpire;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.neow.NeowEvent;

public class CustomModePatch {
    @SpirePatch(
            clz = NeowEvent.class,
            method = "shouldSkipNeowDialog"
    )
    public static class CustomModeShowNeow {
        public static SpireReturn<Boolean> Prefix(NeowEvent __instance) {
            // Skip Neow bonus if it's one of these custom modes, found in NeowEvent.dailyBlessing
            boolean isCustomNeowModSelected = ModHelper.isModEnabled("Specialized") ||
                    ModHelper.isModEnabled("Allstar") ||
                    ModHelper.isModEnabled("Draft") ||
                    ModHelper.isModEnabled("SealedDeck") ||
                    ModHelper.isModEnabled("Heirloom");
            return SpireReturn.Return(isCustomNeowModSelected);
            //return (FilterTheSpire.SEARCHING_FOR_SEEDS) ? SpireReturn.Return(null) : SpireReturn.Continue();
        }

    }
}
