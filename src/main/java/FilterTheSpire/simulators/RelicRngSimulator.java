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

    public HashMap<AbstractRelic.RelicTier, List<String>> getRelicPools(long seed) {
        Random relicRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.RELIC);
        return getRelicPools(relicRng);
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
     * If you pass in a list of relics and list of encounterIndices, the logic is that
     * @param searchRelics: Relic Ids to search for
     * @param seed: Seed to search
     * @param encounterIndex: Which number (zero-indexed) relic we want to find (as in 1st relic or 2nd relic)
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


    /**
     * This should find ALL the search relics in the number of relic encounters specified
     * So find N relics in X relic encounters, not every encounter needs to encounter a search relic as long as all
     * relics are found in the number of relic encounters (ex: find 3 specific relics in 5 relic encounters)
     * This mostly applies to random Relics from events or Elites, because Treasure Chest affects the rarity in a way
     * we can't determine if they'll get the Chest room before/after some number of Relic encounters.
     * @param searchRelics: Relic Ids to search for
     * @param seed: Seed to search
     * @param encounterIndices: ordered list of encounters (so first 3 relic encounters would be [0, 1, 2]
     * @return if Nth relic is in the search relic Ids
     */
    public boolean areNRelicsFoundInXEncounters(List<String> searchRelics, long seed, List<Integer> encounterIndices){
        // Doesn't make sense to search for more relics than we're encountering,
        // if this is the case, remove the relic(s) that are less important instead
        if (searchRelics.size() > encounterIndices.size()){
            throw new IllegalArgumentException();
        }
        Random relicRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.RELIC);
        HashMap<AbstractRelic.RelicTier, List<String>> relicList = getRelicPools(relicRng);

        ArrayList<AbstractRelic.RelicTier> relicRngRarities = new ArrayList<>();
        int encounters = 0;
        ArrayList<String> relicsFound = new ArrayList<>();
        for (int encounterIndex : encounterIndices) {
            // If searching for encounters that aren't sequential (ex: [4, 8, 10]), need to increment the RNG counter between iterations
            while (encounters < encounterIndex){
                relicRngRarities.add(returnRandomRelicTier(relicRng));
                encounters++;
            }
            AbstractRelic.RelicTier encounterRelicTier = returnRandomRelicTier(relicRng);
            int rarityOccurrences = Collections.frequency(relicRngRarities, encounterRelicTier);
            String encounteredRelic = relicList.get(encounterRelicTier).get(rarityOccurrences);
            relicsFound.add(encounteredRelic);
            relicRngRarities.add(encounterRelicTier);
            encounters++;
        }

        return relicsFound.containsAll(searchRelics);
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
