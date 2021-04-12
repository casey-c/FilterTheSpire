package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;

public class ShopRelicRngSimulator {
    public ArrayList<String> shopRelicPool = new ArrayList<>();

    private Random relicRng;

    public String nthShopRelic(int n) {
        return shopRelicPool.get(n);
    }

    public void rerollRelics(long seed) {
        relicRng = new Random(seed);
        this.shopRelicPool = new ArrayList<>();

        // Skip past all these
        relicRng.randomLong(); // uncommon
        relicRng.randomLong(); // rare
        relicRng.randomLong(); // common
        // relicRng.randomLong(); // shop <- this is the one needed (we perform it below)
        // this.relicRng.randomLong(); // boss

        generateShopRelics();
    }

    private void generateShopRelics() {
        RelicLibrary.populateRelicPool(
                this.shopRelicPool,
                AbstractRelic.RelicTier.SHOP,
                AbstractDungeon.player.chosenClass
        );
        Collections.shuffle(this.shopRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.reverse(shopRelicPool); // Shop relics are done in reverse order
    }
}
