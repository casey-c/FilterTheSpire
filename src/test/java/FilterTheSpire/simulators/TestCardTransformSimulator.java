package FilterTheSpire.simulators;

import FilterTheSpire.filters.AbstractFilter;
import FilterTheSpire.filters.AstrolabeCardFilter;
import FilterTheSpire.filters.BlessingFilter;
import FilterTheSpire.filters.PandorasCardFilter;
import FilterTheSpire.utils.cache.RunInfoCache;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.neow.NeowReward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TestCardTransformSimulator {

    @BeforeEach
    public void setup(){
        RunInfoCache.currentCharacter = AbstractPlayer.PlayerClass.DEFECT;
    }

    @Test
    public void testPandorasCardValid(){
        // Defect Pandora's box start has 5 Compile drivers
        long seed = 3271423080934870601L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("Compile Driver", 5);
        AbstractFilter filter = new PandorasCardFilter(searchCards);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testAstrolabeCardValid(){
        // Defect Astrolabe start has 5 Compile drivers
        long seed = -7269851467354856050L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("All For One", 1);
        searchCards.put("Gash", 1);
        searchCards.put("Reprogram", 1);
        AbstractFilter filter = new AstrolabeCardFilter(searchCards);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testTransformOneCardNeowBonus(){
        // Defect Neow transform 1 card into Meteor Strike
        long seed = -4664593965929258954L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("Meteor Strike", 1);
        AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.TRANSFORM_CARD, null, searchCards);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testTransformOneCardNeowBonus_Uncommon(){
        // Defect Neow transform 1 card into Reprogram
        long seed = 4656688986587903285L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("Reprogram", 1);
        AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.TRANSFORM_CARD, null, searchCards);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testTransformOneCardNeowBonus_Common(){
        // Defect Neow transform 1 card into Claw
        long seed = 3060528000905658091L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("Gash", 1);
        AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.TRANSFORM_CARD, null, searchCards);
        assert(filter.isSeedValid(seed));
    }
}
