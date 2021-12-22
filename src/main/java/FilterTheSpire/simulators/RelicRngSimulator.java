package FilterTheSpire.simulators;

import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static final int UncommonRelicRng = 0;
    public static final int RareRelicRng = 1;
    public static final int CommonRelicRng = 2;
    public static final int ShopRelicRng = 3;
    public static final int BossRelicRng = 4;

    public List<String> getRelicPool(long seed, AbstractRelic.RelicTier relicTier, int rngGeneratorLoops) {
        Random relicRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.RELIC);
        return getRelicPool(relicRng, relicTier, rngGeneratorLoops);
    }

    private List<String> getRelicPool(Random relicRng, AbstractRelic.RelicTier relicTier, int rngGeneratorLoops){
        for (int i = 0; i < rngGeneratorLoops; i++) {
            relicRng.randomLong();
        }

        List<String> relicPool = new ArrayList<>(CharacterPoolFactory.getRelicPool(AbstractDungeon.player.chosenClass, relicTier));
        Collections.shuffle(relicPool, new java.util.Random(relicRng.randomLong()));
        return relicPool;
    }

    /**
     * Tries to find the Nth relic drop by simulating the rarities and generates the relic pool for that rarity
     * @param searchRelics: Relic Ids to search for
     * @param seed: Seed to search
     * @param encounterIndex: Which relic we want to find
     * @return if Nth relic is in the search relic Ids
     */
    public boolean isNthRelicValid(List<String> searchRelics, long seed, int encounterIndex){
        Random relicRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.RELIC);
        ArrayList<AbstractRelic.RelicTier> relicRngRarities = new ArrayList<>();
        for (int i = 0; i < encounterIndex; i++){
            relicRngRarities.add(returnRandomRelicTier(relicRng));
        }
        AbstractRelic.RelicTier encounterRelicTier = returnRandomRelicTier(relicRng);
        int rarityOccurrences = Collections.frequency(relicRngRarities, encounterRelicTier);
        int relicPoolRngLoops;
        switch (encounterRelicTier){
            case RARE:
                relicPoolRngLoops = RareRelicRng;
                break;
            case UNCOMMON:
                relicPoolRngLoops = UncommonRelicRng;
                break;
            case COMMON:
                relicPoolRngLoops = CommonRelicRng;
                break;
            default:
                throw new IllegalArgumentException();
        }
        List<String> relicList = getRelicPool(relicRng, encounterRelicTier, relicPoolRngLoops);
        return searchRelics.contains(relicList.get(rarityOccurrences));
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
