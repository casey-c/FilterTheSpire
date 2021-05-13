package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RelicRngSimulator {
    private static final HashMap<AbstractRelic.RelicTier, Integer> RelicRngMapping = new HashMap<AbstractRelic.RelicTier, Integer>(){{
        put(AbstractRelic.RelicTier.UNCOMMON, 0);
        put(AbstractRelic.RelicTier.RARE, 1);
        put(AbstractRelic.RelicTier.COMMON, 2);
        put(AbstractRelic.RelicTier.SHOP, 3);
        put(AbstractRelic.RelicTier.BOSS, 4);
    }};

    public static String getRelic(long seed, AbstractRelic.RelicTier relicTier, int encounterIndex) {
        Random relicRng = new Random(seed);
        ArrayList<String> relicPool = new ArrayList<>();

        // Skip past what we don't want
        // uncommon
        // rare
        // common
        // shop
        // boss
        for (int i = 0; i < RelicRngMapping.get(relicTier); i++) {
            relicRng.randomLong();
        }

        RelicLibrary.populateRelicPool(
            relicPool,
            relicTier,
            AbstractDungeon.player.chosenClass
        );
        Collections.shuffle(relicPool, new java.util.Random(relicRng.randomLong()));

        return relicPool.get(encounterIndex);
    }
}
