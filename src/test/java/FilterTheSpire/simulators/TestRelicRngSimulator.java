package FilterTheSpire.simulators;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.filters.AbstractFilter;
import FilterTheSpire.filters.NthRelicFilter;
import FilterTheSpire.filters.RelicsInEncountersFilter;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class TestRelicRngSimulator {
    private final long seed = 3271423080934870601L;
    private HashMap<AbstractRelic.RelicTier, List<String>> relicPools;

    @BeforeEach
    private void setUp(){
        FilterTheSpire.currentCharacter = AbstractPlayer.PlayerClass.IRONCLAD;
        relicPools = RelicRngSimulator.getInstance().getRelicPools(seed);
    }

    @Test
    public void testKnownPandoraSeed() {
        List<String> bossRelics = relicPools.get(AbstractRelic.RelicTier.BOSS);
        assert(bossRelics.get(0).equals("Pandora's Box"));
        assert(!bossRelics.get(0).equals("Runic Pyramid"));
    }

    @Test
    public void testKnownHelixSeed() {
        List<String> rareRelics = relicPools.get(AbstractRelic.RelicTier.RARE);
        assert(rareRelics.get(0).equals("FossilizedHelix"));
    }

    @Test
    public void testKnownMatryoshkaSeed() {
        List<String> uncommonRelics = relicPools.get(AbstractRelic.RelicTier.UNCOMMON);
        assert(uncommonRelics.get(0).equals("Matryoshka"));
    }

    @Test
    public void testKnownMajorBagSeed() {
        List<String> commonRelics = relicPools.get(AbstractRelic.RelicTier.COMMON);
        assert(commonRelics.get(0).equals("Bag of Preparation"));
    }

    @Test
    public void testKnownPelletsSeed() {
        List<String> shopRelics = relicPools.get(AbstractRelic.RelicTier.SHOP);
        assert(shopRelics.get(shopRelics.size() - 1).equals("OrangePellets"));
    }

    @Test
    public void testOnlyRedRelics() {
        assert(relicPools.get(AbstractRelic.RelicTier.COMMON).contains("Red Skull"));
        assert(!relicPools.get(AbstractRelic.RelicTier.COMMON).contains("Snake Skull"));
    }

    @Test
    public void testNthRelic() {
        AbstractFilter filter = new NthRelicFilter(Collections.singletonList("Vajra"), 3);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testNthRelicInEncounters() {
        AbstractFilter filter = new RelicsInEncountersFilter(Arrays.asList("Matryoshka", "Bag of Preparation", "Happy Flower", "Vajra"), Arrays.asList(0, 1, 2, 3));
        assert(filter.isSeedValid(seed));
    }
}
