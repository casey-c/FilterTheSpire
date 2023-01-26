package FilterTheSpire.utils;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.filters.AbstractFilter;
import FilterTheSpire.filters.BlessingFilter;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.neow.NeowReward;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestCustomModeCardPools {
    /**
     * Diverse does not need every character test
     * Specific color combos should test every rarity and every color in the combo
     *     ex: Green and Blue cards should have 6 combinations for each color and each rarity
     */
    private static class SeededCharacterTransform {
        public AbstractPlayer.PlayerClass character;
        public long seed;
        public HashMap<String, Integer> searchCards;

        public SeededCharacterTransform(AbstractPlayer.PlayerClass character, long seed, List<String> searchCards) {
            this.character = character;
            this.seed = seed;
            HashMap<String, Integer> cardCounts = new HashMap<>();
            for (String s : searchCards) {
                cardCounts.put(s, cardCounts.getOrDefault(s, 0) + 1);
            }
            this.searchCards = cardCounts;
        }
    }

    @Test
    public void testDiverseCardPool_TransformOne(){
        ArrayList<SeededCharacterTransform> seededCharacterTransforms = new ArrayList<>();
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 5202596435497175146L, Collections.singletonList("Anger")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 4997652588297773320L, Collections.singletonList("Battle Trance")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 3950493551078825252L, Collections.singletonList("Barricade")));

        ModHelper.initialize();
        ModHelper.setMods(Collections.singletonList("Diverse"));
        for (SeededCharacterTransform test: seededCharacterTransforms) {
            FilterTheSpire.currentCharacter = test.character;
            AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.TRANSFORM_CARD, null, test.searchCards);
            assert(filter.isSeedValid(test.seed));
        }
    }

    // TODO: do Defect and Watcher cards for IC
    // TODO: do Silent, defect, and watcher cards as Silent
    // TODO: retest the IC cards with these mods
    @Test
    public void testColorComboCardPool_TransformOne(){
        ArrayList<SeededCharacterTransform> seededCharacterTransforms = new ArrayList<>();
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 5202596435497175146L, Collections.singletonList("Anger")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 4997652588297773320L, Collections.singletonList("Battle Trance")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 3950493551078825252L, Collections.singletonList("Barricade")));

        ModHelper.setMods(Arrays.asList("Blue Cards", "Purple Cards"));
        for (SeededCharacterTransform test: seededCharacterTransforms) {
            FilterTheSpire.currentCharacter = test.character;
            AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.TRANSFORM_CARD, null, test.searchCards);
            filter.isSeedValid(test.seed);
        }
    }

    // TODO: Test pandoras diverse and color combos
}
