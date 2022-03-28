package FilterTheSpire;

import FilterTheSpire.filters.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import java.util.*;

@SpireInitializer
public class FilterManager {
    // Singleton pattern
    private static class FilterManagerHolder { private static final FilterManager INSTANCE = new FilterManager(); }
    private static FilterManager getInstance() { return FilterManagerHolder.INSTANCE; }
    public static void initialize() { getInstance(); }

    private static HashMap<String, AbstractFilter> filters = new HashMap<>();
    private static List<AbstractFilter> sortedList = null;

    // Returns true if all filters pass for the given seed
    public static boolean validateFilters(long seed) {
        return sortedList.stream().allMatch(v -> v.isSeedValid(seed));
    }

    // Sort the filters so we short circuit on the least intensive filters first
    public static void sortFilters(){
        sortedList = new ArrayList<>(filters.values());
        sortedList.sort(Comparator.comparingInt(AbstractFilter::getSortOrder));
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

    public static void setBossSwapIs(String relic, int encounterIndex) {
        if (!relic.isEmpty()){
            NthBossRelicFilter filter = new NthBossRelicFilter(Collections.singletonList(relic), encounterIndex);
            filters.put("bossSwapIsOneOf" + encounterIndex, filter);
        } else {
            filters.remove("bossSwapIsOneOf" + encounterIndex);
        }
    }

    public static void setBossSwapFiltersFromValidList(ArrayList<String> relicIDs) {
        if (relicIDs.size() > 0){
            NthBossRelicFilter filter = new NthBossRelicFilter(relicIDs);
            filters.put("bossSwapIsOneOf", filter);
        } else {
            filters.remove("bossSwapIsOneOf");
        }
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

    public static void setPandorasCardFilter(HashMap<String, Integer> searchCards) {
        filters.put("pandorasTmp", new PandorasCardFilter(searchCards));
    }

    public static void setAstrolabeCardFilter(HashMap<String, Integer> searchCards) {
        filters.put("astrolabeFilter", new AstrolabeCardFilter(searchCards));
    }

    // --------------------------------------------------------------------------------

    public static void setFirstShopRelicIs(String relic) {
        NthShopRelicFilter filter = new NthShopRelicFilter(Collections.singletonList(relic));
        setValidatorFromString("shopRelicIs", filter);
    }

    public static void setShopFiltersFromValidList(ArrayList<String> relicIds) {
        if (relicIds.size() > 0){
            NthShopRelicFilter filter = new NthShopRelicFilter(relicIds);
            filters.put("shopRelicIsOneOf", filter);
        } else {
            filters.remove("shopRelicIsOneOf");
        }
    }

    // --------------------------------------------------------------------------------

    public static void setNthRelicFromValidList(ArrayList<String> relicIds) {
        setNthRelicFromValidList(relicIds, 0);
    }

    public static void setNthRelicFromValidList(ArrayList<String> relicIds, int encounterIndex) {
        if (relicIds.size() > 0) {
            NeowRelicFilter filter = new NeowRelicFilter(relicIds, encounterIndex);
            filters.put("nthRelicIsOneOf" + encounterIndex, filter);
        } else {
            filters.remove("nthRelicIsOneOf" + encounterIndex);
        }
    }

    // --------------------------------------------------------------------------------

    public static void print() {
        System.out.println("FilterManager has " + filters.size() + " filters");
    }
}
