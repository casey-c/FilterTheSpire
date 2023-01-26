package FilterTheSpire.simulators;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.filters.AbstractFilter;
import FilterTheSpire.filters.BlessingFilter;
import FilterTheSpire.filters.NthCardRewardFilter;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.neow.NeowReward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

public class TestCardRngSimulator {
    @BeforeEach
    public void setup(){
        FilterTheSpire.currentCharacter = AbstractPlayer.PlayerClass.THE_SILENT;
    }

    @Test
    public void testIsNthCardRewardValid(){
        // First card reward as Silent is Backstab, Blade dance, and Prepared
        long seed = 3271423080934870601L;
        AbstractFilter filter = new NthCardRewardFilter(Arrays.asList("Blade Dance", "Prepared", "Backstab"), 0);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testIsValidCardRewardFromNeow(){
        // card reward from Neow is Prepared, Outmaneuver, and Dodge and Roll
        long seed = 8216573587627365668L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("Prepared", 1);
        searchCards.put("Outmaneuver", 1);
        searchCards.put("Dodge and Roll", 1);
        AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.THREE_CARDS, null, searchCards);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testIsValidCardRewardFromNeow_Rare(){
        // card reward from Neow is Adrenaline, Burst, Phantasmal Killer
        long seed = 1165838142192484830L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("Adrenaline", 1);
        searchCards.put("Burst", 1);
        searchCards.put("Phantasmal Killer", 1);
        AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.THREE_RARE_CARDS, null, searchCards);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testIsValidColorlessCardFromNeow_Uncommon(){
        // card reward from Neow is Dark Shackles, Enlightenment, Panacea
        long seed = 8474596763883045790L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("Dark Shackles", 1);
        searchCards.put("Enlightenment", 1);
        searchCards.put("Panacea", 1);
        AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.RANDOM_COLORLESS, null, searchCards);
        assert(filter.isSeedValid(seed));
    }

    @Test
    public void testIsValidColorlessCardFromNeow_Rare(){
        // card reward from Neow is Panache, Apotheosis, Transmutation
        long seed = 6015795624912855724L;
        HashMap<String, Integer> searchCards = new HashMap<>();
        searchCards.put("Panache", 1);
        searchCards.put("Apotheosis", 1);
        searchCards.put("Transmutation", 1);
        AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.RANDOM_COLORLESS_2, null, searchCards);
        assert(filter.isSeedValid(seed));
    }
}
