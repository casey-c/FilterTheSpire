package FilterTheSpire;

import FilterTheSpire.filters.*;
import FilterTheSpire.simulators.RelicRngSimulator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    // --------------------------------------------------------------------------------

    public static void setORValidator(String validatorName, ArrayList<AbstractFilter> filtersToMatch) {
        OrFilter filter = new OrFilter(filtersToMatch);
        filters.put(validatorName, filter);
    }

    public static void setValidatorFromString(String validatorName, AbstractFilter filter) {
        filters.put(validatorName, filter);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstCombatIs(String enemyName) {
        ArrayList<String> combatOrder = new ArrayList<>();
        combatOrder.add(enemyName);
        CombatOrderFilter filter = new CombatOrderFilter(combatOrder);
        setValidatorFromString("firstCombatIs", filter);
    }

    public static void setFirstCombatsAre(ArrayList<String> enemyNames) {
        ArrayList<String> combatOrder = enemyNames;
        CombatOrderFilter filter = new CombatOrderFilter(combatOrder);
        setValidatorFromString("firstCombatsAre", filter);
    }

    // --------------------------------------------------------------------------------

    public static void setBossSwapIs(String relic) {
        FirstBossRelicFilter filter = new FirstBossRelicFilter(relic);
        setValidatorFromString("bossSwapIs", filter);
    }

    public static void setBossSwapFiltersFromValidList(ArrayList<String> relicIDs) {
        ArrayList<AbstractFilter> filtersToCheck = new ArrayList<>();
        for (String relicID : relicIDs) {
            FirstBossRelicFilter filter = new FirstBossRelicFilter(relicID);
            filtersToCheck.add(filter);
        }
        setORValidator("firstBossIsOneOf", filtersToCheck);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstBossIs(String bossName) {
        FirstBossFilter filter = new FirstBossFilter(bossName);
        filters.put("firstBoss", filter);
    }

    public static void setFirstBossIsOneOf(ArrayList<String> bossNames) {
        ArrayList<AbstractFilter> filtersToCheck = new ArrayList<>();
        for (String bossName : bossNames) {
            FirstBossFilter filter = new FirstBossFilter(bossName);
            filtersToCheck.add(filter);
        }
        setORValidator("firstBossIsOneOf", filtersToCheck);
    }

    // --------------------------------------------------------------------------------

    public static void setFirstEliteIs(String eliteName) {
        FirstEliteFilter filter = new FirstEliteFilter(eliteName);
        filters.put("firstElite", filter);
    }

    public static void setFirstEliteIsOneOf(ArrayList<String> eliteNames) {
        ArrayList<AbstractFilter> filtersToCheck = new ArrayList<>();
        for (String eliteName : eliteNames) {
            FirstEliteFilter filter = new FirstEliteFilter(eliteName);
            filtersToCheck.add(filter);
        }
        setORValidator("firstEliteIsOneOf", filtersToCheck);
    }

    // --------------------------------------------------------------------------------

    public static void print() {
        System.out.println("FilterManager has " + filters.size() + " filters");
    }
}
