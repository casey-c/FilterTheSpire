package FilterTheSpire;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Stream;

@SpireInitializer
public class FilterManager {
    // Singleton pattern
    private static class FilterManagerHolder { private static final FilterManager INSTANCE = new FilterManager(); }
    private static FilterManager getInstance() { return FilterManagerHolder.INSTANCE; }
    public static void initialize() { getInstance(); }

    //private static ArrayList<BooleanSupplier> validators = new ArrayList<>();
    private static ArrayList<Function<Long, Boolean>> validators = new ArrayList<>();

    // Returns true iff all validation functions are true
    public static boolean validateFilters(long seed) {
        //return validators.stream().map(v -> v.apply(seed)).allMatch(Boolean::booleanValue);
        return validators.stream().allMatch(v -> v.apply(seed));
    }

    public static void setBossSwapFiltersFromValidList(ArrayList<String> relicIDs) {
        validators.clear();

        ArrayList<Function<Long, Boolean>> group = new ArrayList<>();
        for (String id : relicIDs)
            group.add((seed) -> bossSwapIs(seed, id));

//        ArrayList<BooleanSupplier> group = new ArrayList<>();
//
//        for (String id : relicIDs)
//            group.add( () -> bossSwapIs(id));

        //validators.add(() -> group.stream().anyMatch(BooleanSupplier::getAsBoolean));
        validators.add((seed) -> group.stream().anyMatch(v -> v.apply(seed)));
    }

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

    public static void print() {
        System.out.println("FilterManager has " + validators.size() + " filters");
    }
}
