package FilterTheSpire.utils.helpers;

import FilterTheSpire.FilterManager;
import basemod.BaseMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

public class SeedHelper {
    public static Random getNewRNG(long seed, RNGType rngType){
        Random rng;
        if (FilterManager.hasFilters()){
            boolean hasRNGFixMod = BaseMod.hasModID("rngFix:");

            if (rngType == RNGType.NEOW && hasRNGFixMod){
                rng = new Random(seed, 13);
            } else {
                rng = new Random(seed);
            }

            // compatibility with RNG Fix
            if (hasRNGFixMod){
                if (rngType != RNGType.NEOW){
                    for (int i = 0; i < rngType.ordinal(); i++){
                        rng.random.nextLong();
                    }
                }
                rng = new Random(rng.random.nextLong());
            }
        } else {
            rng = new Random(seed);
        }
        return rng;
    }

    // Most of these we won't need, but this is the order the RNG is generated in RNG Fix
    public enum RNGType {
        MONSTER,
        EVENT,
        MERCHANT,
        CARD,
        TREASURE,
        RELIC,
        POTION,
        MONSTERHP,
        AI,
        SHUFFLE,
        CARDRANDOM,
        MISC,
        NEOW
    }
}
