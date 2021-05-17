package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RelicRngSimulator {
    public static final int UncommonRelicRng = 0;
    public static final int RareRelicRng = 1;
    public static final int CommonRelicRng = 2;
    public static final int ShopRelicRng = 3;
    public static final int BossRelicRng = 4;

    public static ArrayList<String> getRelicPool(long seed, AbstractRelic.RelicTier relicTier, int rngGeneratorLoops) {
        Random relicRng = new Random(seed);
        ArrayList<String> relicPool = new ArrayList<>();

        for (int i = 0; i < rngGeneratorLoops; i++) {
            relicRng.randomLong();
        }

        RelicLibrary.populateRelicPool(
            relicPool,
            relicTier,
            AbstractDungeon.player.chosenClass
        );
        Collections.shuffle(relicPool, new java.util.Random(relicRng.randomLong()));
        return relicPool;
    }
}
