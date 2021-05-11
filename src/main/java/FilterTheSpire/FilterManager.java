package FilterTheSpire;

import FilterTheSpire.filters.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import java.util.ArrayList;
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

    public static void setORValidator(String validatorName, ArrayList<AbstractFilter> filtersToMatch) {
        if (filtersToMatch.isEmpty()) {
            // Need to 'forget' the existing validator if it exists - and then there's no need to put an empty OR
            filters.remove(validatorName);
            return;
        }

        OrFilter filter = new OrFilter(filtersToMatch);
        filters.put(validatorName, filter);
    }

    public static void setValidatorFromString(String validatorName, AbstractFilter filter) {
        filters.put(validatorName, filter);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstCombatIs(String enemyName) {
        NthCombatFilter filter = new NthCombatFilter(enemyName);
        setValidatorFromString("firstCombatIs", filter);
    }

//    public static void setFirstCombatsAre(ArrayList<String> enemyNames) {
//        ArrayList<String> combatOrder = enemyNames;
//        NthCombatFilter filter = new NthCombatFilter(combatOrder);
//        setValidatorFromString("firstCombatsAre", filter);
//    }

    // --------------------------------------------------------------------------------

    public static void setBossSwapIs(String relic) {
        NthBossRelicFilter filter = new NthBossRelicFilter(relic);
        setValidatorFromString("bossSwapIs", filter);
    }

    public static void setBossSwapFiltersFromValidList(ArrayList<String> relicIDs) {
        ArrayList<AbstractFilter> filtersToCheck = new ArrayList<>();
        for (String relicID : relicIDs) {
            NthBossRelicFilter filter = new NthBossRelicFilter(relicID);
            filtersToCheck.add(filter);
        }

        setORValidator("firstBossIsOneOf", filtersToCheck);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstBossIs(String bossName) {
        BossFilter filter = new BossFilter(bossName);
        filters.put("firstBoss", filter);
    }

    public static void setFirstBossIsOneOf(ArrayList<String> bossNames) {
        ArrayList<AbstractFilter> filtersToCheck = new ArrayList<>();
        for (String bossName : bossNames) {
            BossFilter filter = new BossFilter(bossName);
            filtersToCheck.add(filter);
        }
        setORValidator("firstBossIsOneOf", filtersToCheck);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstEliteIs(String eliteName) {
        NthEliteFilter filter = new NthEliteFilter(eliteName);
        filters.put("firstElite", filter);
    }

    public static void setFirstEliteIsOneOf(ArrayList<String> eliteNames) {
        ArrayList<AbstractFilter> filtersToCheck = new ArrayList<>();
        for (String eliteName : eliteNames) {
            NthEliteFilter filter = new NthEliteFilter(eliteName);
            filtersToCheck.add(filter);
        }
        setORValidator("firstEliteIsOneOf", filtersToCheck);
    }

    public static void testPandoras() {
        filters.put("pandorasTmp", new PandorasCardFilter());
    }

    // --------------------------------------------------------------------------------

    public static void setFirstShopRelicIs(String relic) {
        NthShopRelicFilter filter = new NthShopRelicFilter(relic);
        setValidatorFromString("shopRelicIs", filter);
    }

    public static void setShopFiltersFromValidList(ArrayList<String> relicIDs) {
        ArrayList<AbstractFilter> filtersToCheck = new ArrayList<>();
        for (String relicID : relicIDs) {
            NthShopRelicFilter filter = new NthShopRelicFilter(relicID);
            filtersToCheck.add(filter);
        }

        setORValidator("shopRelicIsOneOf", filtersToCheck);
    }

    // --------------------------------------------------------------------------------

    public static void print() {
        System.out.println("FilterManager has " + filters.size() + " filters");
    }
}
