package FilterTheSpire.simulators;

import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.*;

public class RelicRngSimulator {
    private static RelicRngSimulator singleton = null;

    public static RelicRngSimulator getInstance(){
        if (singleton == null){
            singleton = new RelicRngSimulator();
        }
        return singleton;
    }

    private RelicRngSimulator(){

    }

    public List<String> getRelicPools(long seed, AbstractRelic.RelicTier relicTier) {
        Random relicRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.RELIC);
        return getRelicPools(relicRng).get(relicTier);
    }

    private HashMap<AbstractRelic.RelicTier, List<String>> getRelicPools(Random relicRng) {
        HashMap<AbstractRelic.RelicTier, List<String>> map = new HashMap<>();
        List<AbstractRelic.RelicTier> rarities = Arrays.asList(AbstractRelic.RelicTier.COMMON, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.RelicTier.RARE, AbstractRelic.RelicTier.SHOP, AbstractRelic.RelicTier.BOSS);
        for (AbstractRelic.RelicTier rarity: rarities) {
            List<String> relicPool = new ArrayList<>(CharacterPoolFactory.getRelicPool(AbstractDungeon.player.chosenClass, rarity));
            Collections.shuffle(relicPool, new java.util.Random(relicRng.randomLong()));
            map.put(rarity, relicPool);
        }

        return map;
    }

    /**
     * Tries to find the Nth relic drop by simulating the rarities and generates the relic pool for that rarity
     * @param searchRelics: Relic Ids to search for
     * @param seed: Seed to search
     * @param encounterIndex: Which number relic we want to find
     * @return if Nth relic is in the search relic Ids
     */
    public boolean isNthRelicValid(List<String> searchRelics, long seed, int encounterIndex){
        Random relicRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.RELIC);
        HashMap<AbstractRelic.RelicTier, List<String>> relicList = getRelicPools(relicRng);

        ArrayList<AbstractRelic.RelicTier> relicRngRarities = new ArrayList<>();
        for (int i = 0; i < encounterIndex; i++){
            relicRngRarities.add(returnRandomRelicTier(relicRng));
        }

        AbstractRelic.RelicTier encounterRelicTier = returnRandomRelicTier(relicRng);
        int rarityOccurrences = Collections.frequency(relicRngRarities, encounterRelicTier);

        return searchRelics.contains(relicList.get(encounterRelicTier).get(rarityOccurrences));
    }

    private AbstractRelic.RelicTier returnRandomRelicTier(Random relicRng) {
        int roll = relicRng.random(0, 99);

        if (roll < 50) {
            return AbstractRelic.RelicTier.COMMON;
        } else {
            return roll > 82 ? AbstractRelic.RelicTier.RARE : AbstractRelic.RelicTier.UNCOMMON;
        }
    }
}
