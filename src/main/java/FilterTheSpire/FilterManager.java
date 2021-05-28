package FilterTheSpire;

import FilterTheSpire.filters.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SpireInitializer
public class FilterManager {
    // Singleton pattern
    private static class FilterManagerHolder { private static final FilterManager INSTANCE = new FilterManager(); }
    private static FilterManager getInstance() { return FilterManagerHolder.INSTANCE; }
    public static void initialize() { getInstance(); }

    private static HashMap<String, AbstractFilter> filters = new HashMap<>();

    // Returns true if all filters pass for the given seed
    public static boolean validateFilters(long seed) {
        return filters.values().stream().allMatch(v -> v.isSeedValid(seed));
    }

    public static boolean hasFilters() {
        return filters.size() > 0;
    }

    public static int numFilters() {
        return filters.size();
    }

    // --------------------------------------------------------------------------------

    public static void setValidatorFromString(String validatorName, AbstractFilter filter) {
        filters.put(validatorName, filter);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstCombatIs(String enemyName) {
        NthCombatFilter filter = new NthCombatFilter(Collections.singletonList(enemyName));
        setValidatorFromString("firstCombatIs", filter);
    }

//    public static void setFirstCombatsAre(ArrayList<String> enemyNames) {
//        ArrayList<String> combatOrder = enemyNames;
//        NthCombatFilter filter = new NthCombatFilter(combatOrder);
//        setValidatorFromString("firstCombatsAre", filter);
//    }

    // --------------------------------------------------------------------------------

    public static void setBossSwapIs(String relic) {
        NthBossRelicFilter filter = new NthBossRelicFilter(Collections.singletonList(relic));
        setValidatorFromString("bossSwapIs", filter);
    }

    public static void setBossSwapFiltersFromValidList(ArrayList<String> relicIDs) {
        NthBossRelicFilter filter = new NthBossRelicFilter(relicIDs);
        filters.put("bossSwapIsOneOf", filter);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstBossIs(String bossName) {
        BossFilter filter = new BossFilter(Collections.singletonList(bossName));
        filters.put("firstBoss", filter);
    }

    public static void setFirstBossIsOneOf(ArrayList<String> bossNames) {
        BossFilter filter = new BossFilter(bossNames);
        filters.put("firstBossIsOneOf", filter);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstEliteIs(String eliteName) {
        NthEliteFilter filter = new NthEliteFilter(Collections.singletonList(eliteName));
        filters.put("firstElite", filter);
    }

    public static void setFirstEliteIsOneOf(ArrayList<String> eliteNames) {
        NthEliteFilter filter = new NthEliteFilter(eliteNames);
        filters.put("firstEliteIsOneOf", filter);
    }

    public static void testPandoras() {
        filters.put("pandorasTmp", new PandorasCardFilter());
    }

    // --------------------------------------------------------------------------------

    public static void setFirstShopRelicIs(String relic) {
        NthShopRelicFilter filter = new NthShopRelicFilter(Collections.singletonList(relic));
        setValidatorFromString("shopRelicIs", filter);
    }

    public static void setShopFiltersFromValidList(ArrayList<String> relicIds) {
        NthShopRelicFilter filter = new NthShopRelicFilter(relicIds);
        filters.put("shopRelicIsOneOf", filter);
    }

    // --------------------------------------------------------------------------------

    public static void setNthRelicFromValidList(ArrayList<String> relicIds) {
        NthRelicFilter filter = new NthRelicFilter(relicIds);
        filters.put("nthRelicIsOneOf", filter);
    }

    // --------------------------------------------------------------------------------

    public static void print() {
        System.out.println("FilterManager has " + filters.size() + " filters");
    }
}
