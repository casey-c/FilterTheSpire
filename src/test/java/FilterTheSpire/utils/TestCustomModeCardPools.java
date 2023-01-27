package FilterTheSpire.utils;

import FilterTheSpire.filters.AbstractFilter;
import FilterTheSpire.filters.BlessingFilter;
import FilterTheSpire.filters.PandorasCardFilter;
import FilterTheSpire.utils.cache.RunInfoCache;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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

        RunInfoCache.modList = Collections.singletonList("Diverse");
        for (SeededCharacterTransform test: seededCharacterTransforms) {
            RunInfoCache.currentCharacter = test.character;
            AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.TRANSFORM_CARD, null, test.searchCards);
            assert(filter.isSeedValid(test.seed));
        }
    }

    @Test
    public void testColorComboCardPool_TransformOne(){
        ArrayList<SeededCharacterTransform> seededCharacterTransforms = new ArrayList<>();
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -8268583500235188578L, Collections.singletonList("Body Slam")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -400157497897147904L, Collections.singletonList("Battle Trance")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -6650950712660469086L, Collections.singletonList("Barricade")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 7692193227307040963L, Collections.singletonList("Ball Lightning")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 3884344816506100305L, Collections.singletonList("Blizzard")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -809640624072603161L, Collections.singletonList("Seek")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 7983022889559420055L, Collections.singletonList("CutThroughFate")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -7572092901274521722L, Collections.singletonList("Wallop")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 7632246877775832614L, Collections.singletonList("Alpha")));

        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 8266179968509807235L, Collections.singletonList("Backflip")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 2641291110936824436L, Collections.singletonList("All Out Attack")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 8337709442112374460L, Collections.singletonList("Venomology")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, -1240839941842358392L, Collections.singletonList("Beam Cell")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 6440355012388430578L, Collections.singletonList("Defragment")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 6299972594868712636L, Collections.singletonList("Multi-Cast")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, -235714968482285686L, Collections.singletonList("PathToVictory")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 5848464572777142619L, Collections.singletonList("Wireheading")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 4457535042856711863L, Collections.singletonList("Omniscience")));

        RunInfoCache.modList = Arrays.asList("Blue Cards", "Purple Cards");
        for (SeededCharacterTransform test: seededCharacterTransforms) {
            RunInfoCache.currentCharacter = test.character;
            AbstractFilter filter = new BlessingFilter(NeowReward.NeowRewardType.TRANSFORM_CARD, null, test.searchCards);
            assert(filter.isSeedValid(test.seed));
        }
    }

    @Test
    public void testDiverseCardPool_Pandoras(){
        ArrayList<SeededCharacterTransform> seededCharacterTransforms = new ArrayList<>();
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -7825557313266931991L,
                Arrays.asList("Adrenaline", "Conclude", "Brilliance", "Well Laid Plans", "Feed", "After Image", "Electrodynamics", "BowlingBash", "Creative AI")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, 7212345179691260157L,
                Arrays.asList("Outmaneuver", "Dagger Spray", "Endless Agony", "Dagger Throw", "WheelKick", "Force Field", "Demon Form", "Flame Barrier", "Phantasmal Killer")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -4840622081793768909L,
                Arrays.asList("LikeWater", "Dark Embrace", "Doppelganger", "Footwork", "Chill", "Sword Boomerang", "Fasting2", "Barrage", "Recycle")));

        RunInfoCache.modList = Collections.singletonList("Diverse");
        for (SeededCharacterTransform test: seededCharacterTransforms) {
            RunInfoCache.currentCharacter = test.character;
            AbstractFilter filter = new PandorasCardFilter(test.searchCards);
            assert(filter.isSeedValid(test.seed));
        }
    }

    @Test
    public void testColorComboCardPool_Pandoras(){
        ArrayList<SeededCharacterTransform> seededCharacterTransforms = new ArrayList<>();
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -9173227664313642475L,
                Arrays.asList("Double Tap", "Seeing Red", "Iron Wave", "Auto Shields", "Brilliance", "Battle Trance", "LikeWater", "Biased Cognition", "Sweeping Beam")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.IRONCLAD, -3150534459797038717L,
                Arrays.asList("Shockwave", "Sweeping Beam", "LessonLearned", "Vengeance", "Core Surge", "EmptyFist", "Sanctity", "Flex", "Meditate")));

        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 7487988066512192058L,
                Arrays.asList("Darkness", "Wish", "Doom and Gloom", "Distraction", "Masterful Stab", "Meditate", "Study", "Compile Driver", "CutThroughFate", "SpiritShield")));
        seededCharacterTransforms.add(new SeededCharacterTransform(AbstractPlayer.PlayerClass.THE_SILENT, 1502838640664874076L,
                Arrays.asList("Core Surge", "Nirvana", "Dagger Throw", "Grand Finale", "Chill", "Biased Cognition", "Alpha", "Force Field", "Dagger Spray", "Prepared")));

        RunInfoCache.modList = Arrays.asList("Blue Cards", "Purple Cards");
        for (SeededCharacterTransform test: seededCharacterTransforms) {
            RunInfoCache.currentCharacter = test.character;
            AbstractFilter filter = new PandorasCardFilter(test.searchCards);
            assert(filter.isSeedValid(test.seed));
        }
    }
}
