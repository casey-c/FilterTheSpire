package FilterTheSpire.utils;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;

// TODO: DEBUG ONLY / REMOVE
public class SeedTesting {
    // TODO: edit: this works!
    public static void testing() {
        /*
        // AbstractDungeon::generateSeeds();
        relicRng = new Random(Settings.seed);

        // AbstractDungeon::initializeRelicList
        //   As far as I can tell, nothing happens to the rng until these shuffles
        Collections.shuffle(commonRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(uncommonRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(rareRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(shopRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));

        // Idea: take the seed; build the boss relic pool as normal and try and see if the result of my shuffle
        //   is the same
         */

        Long seed = Settings.seed;
        Random relicRng = new Random(seed);

        // Skip past all these
        relicRng.randomLong(); // common
        relicRng.randomLong(); // uncommon
        relicRng.randomLong(); // rare
        relicRng.randomLong(); // shop
        //relicRng.randomLong(); // boss <- this is the one (we perform it below)

        ArrayList<String> bossRelicPool = new ArrayList<>();
        RelicLibrary.populateRelicPool(bossRelicPool, AbstractRelic.RelicTier.BOSS, AbstractDungeon.player.chosenClass);

        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));

        // These are identical - hooray!
        ArrayList<String> realPool = AbstractDungeon.bossRelicPool;
        System.out.println("**************************************");
        System.out.println("The real boss relic pool is: " + realPool.toString());
        System.out.println("My manufactured boss relic pool is: " + bossRelicPool.toString());
        System.out.println("**************************************\n");
    }
}
