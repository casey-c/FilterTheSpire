package FilterTheSpire;

import FilterTheSpire.rng.MonsterRngHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Stream;

@SpireInitializer
public class FilterManager {
    // Singleton pattern
    private static class FilterManagerHolder { private static final FilterManager INSTANCE = new FilterManager(); }
    private static FilterManager getInstance() { return FilterManagerHolder.INSTANCE; }
    public static void initialize() { getInstance(); }

    //private static ArrayList<Function<Long, Boolean>> validators = new ArrayList<>();
    private static HashMap<String, Function<Long, Boolean>> validators = new HashMap<>();

    // Returns true iff all validation functions are true
    public static boolean validateFilters(long seed) {
        return validators.values().stream().allMatch(v -> v.apply(seed));
    }

    public static boolean hasFilters() {
        return validators.size() > 0;
    }

    // --------------------------------------------------------------------------------

    // What it does:
    //   Assume we have a list of Strings [s1, s2, s3] and a seed
    //
    //   We want a way to use our specific functions (passed in as validatorFn) on each string and return true if any of
    //   them work with the given seed. This is done by creating a "group" of functions that apply the valid seed tests
    //   to a single string - the group will have a function call to test s1 against the seed, s2 against the seed, etc.
    //
    //   We use this group to produce one final validator function (i.e. one that takes in a seed and reports if it is
    //   valid or not) - that essentially is the combo of all these individual members of the group - "anyMatch" here
    //   is if any element in our given list of strings fits the filter. Hence, the OR.
    //
    //  I'm writing this as 4 A.M. with the full knowledge that I will have no clue what this witchcraft I came up
    //  with will do at any point in the future. Look at this syntax and cry, future me!
    public static void setORValidatorFromList(String validatorName, BiFunction<Long, String, Boolean> validatorFn, ArrayList<String> list) {
        ArrayList<Function<Long, Boolean>> group = new ArrayList<>();
        for (String s : list)
            group.add((seed) -> validatorFn.apply(seed, s));

        validators.put(validatorName, (seed) -> group.stream().anyMatch(v -> v.apply(seed)));
    }

    public static void setValidatorFromString(String validatorName, BiFunction<Long, String, Boolean> validatorFn, String target) {
        validators.put(validatorName, (seed) -> validatorFn.apply(seed, target));
    }

    // --------------------------------------------------------------------------------

    public static void setBossSwapIs(String relic) { setValidatorFromString("bossSwap", FilterManager::bossSwapIs, relic); }
    public static void setBossSwapFiltersFromValidList(ArrayList<String> relicIDs) { setORValidatorFromList("bossSwap", FilterManager::bossSwapIs, relicIDs); }

    private static boolean bossSwapIs(long seed, String targetRelic) {
        Random relicRng = new Random(seed);

        // Skip past all these
        relicRng.randomLong(); // common
        relicRng.randomLong(); // uncommon
        relicRng.randomLong(); // rare
        relicRng.randomLong(); // shop
        //relicRng.randomLong(); // boss <- this is the one needed (we perform it below)

        ArrayList<String> bossRelicPool = new ArrayList<>();
        RelicLibrary.populateRelicPool(bossRelicPool, AbstractRelic.RelicTier.BOSS, AbstractDungeon.player.chosenClass);
        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));

        return !bossRelicPool.isEmpty() && bossRelicPool.get(0).equals(targetRelic);
    }

    // --------------------------------------------------------------------------------

    public static void setEarlyRelicsAre(ArrayList<String> relicIDs) {
        setORValidatorFromList("earlyRelic", FilterManager::earlyRelicIs, relicIDs);
    }

    private static boolean earlyRelicIs(long seed, String targetRelic) {
        // TODO
        return false;
    }

    // --------------------------------------------------------------------------------

    public static void setFirstBossIs(String boss) { setValidatorFromString("firstBoss", FilterManager::firstBossIs, boss); }
    public static void setFirstBossesAre(ArrayList<String> bossNames) { setORValidatorFromList("firstBoss", FilterManager::firstBossIs, bossNames); }

    public static boolean firstBossIs(long seed, String bossName) {
        return new MonsterRngHelper(seed).firstBossIs(bossName);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstEliteIs(String elite) { setValidatorFromString("firstElite", FilterManager::firstEliteIs, elite); }
    public static void setFirstElitesAre(ArrayList<String> elites) { setORValidatorFromList("firstElite", FilterManager::firstEliteIs, elites); }

    public static boolean firstEliteIs(long seed, String elite) {
        return new MonsterRngHelper(seed).firstEliteIs(elite);
    }

    // --------------------------------------------------------------------------------

    public static void print() {
        System.out.println("FilterManager has " + validators.size() + " filters");
    }
}
