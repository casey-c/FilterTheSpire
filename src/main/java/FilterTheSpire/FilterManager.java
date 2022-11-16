package FilterTheSpire;

import FilterTheSpire.filters.*;
import FilterTheSpire.utils.SeedHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * This is for RNG counters we increment from Neow (such as card rewards incrementing card RNG).
     */
    public static HashMap<SeedHelper.RNGType, Integer> preRngCounters = new HashMap<>();

    /**
     * This is for any relic filters you want to add on top of getting relic(s) from Neow.
     * Use the relic filters as if you weren't getting anything from Neow
     * Ex: If you want the Rare Relic from Neow to be Dead Branch and first Rare relic encounter to be
     *     Fossilized Helix, then use the Relic encounter filter for 0th index for Helix and add the
     *     Blessing filter for Rare relic Dead Branch
     */
    public static HashMap<AbstractRelic.RelicTier, Integer> neowBonusRelicsCount = new HashMap<>();

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

    public static void setBossSwapIs(String relic, List<Integer> possibleEncounterIndices) {
        String indices = possibleEncounterIndices.stream().map(String::valueOf).collect(Collectors.joining(""));
        if (!relic.isEmpty()){
            NthBossRelicFilter filter = new NthBossRelicFilter(Collections.singletonList(relic), possibleEncounterIndices);
            filters.put("bossSwapIsOneOf" + indices, filter);
        } else {
            filters.remove("bossSwapIsOneOf" + indices);
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

    public static void setShopRelicFilter(String relic, int encounterIndex) {
        setShopFiltersFromValidList(Collections.singletonList(relic), encounterIndex);
    }

    public static void setShopFiltersFromValidList(List<String> relicIds, int encounterIndex) {
        if (relicIds.size() > 0){
            NthShopRelicFilter filter = new NthShopRelicFilter(relicIds, encounterIndex);
            filters.put("shopRelicIsOneOf"+encounterIndex, filter);
        } else {
            filters.remove("shopRelicIsOneOf"+encounterIndex);
        }
    }

    // --------------------------------------------------------------------------------

    public static void setNthRelicFromValidList(ArrayList<String> relicIds) {
        setNthRelicFromValidList(relicIds, 0);
    }

    public static void setNthRelicFromValidList(ArrayList<String> relicIds, int encounterIndex) {
        if (relicIds.size() > 0) {
            NthRelicFilter filter = new NthRelicFilter(relicIds, encounterIndex);
            filters.put("nthRelicIsOneOf" + encounterIndex, filter);
        } else {
            filters.remove("nthRelicIsOneOf" + encounterIndex);
        }
    }

    public static void addEventFilter(String event){
        if (event != null && event.length() > 0) {
            Act1EventFilter filter = new Act1EventFilter(event);
            filters.put("eventFilter", filter);
        } else {
            filters.remove("eventFilter");
        }
    }

    public static void setRelicsInEncounters(ArrayList<String> relicIds, List<Integer> encounterIndices) {
        String indices = encounterIndices.stream().map(String::valueOf).collect(Collectors.joining(""));
        if (relicIds.size() > 0) {
            RelicsInEncountersFilter filter = new RelicsInEncountersFilter(relicIds, encounterIndices);
            filters.put("relicInEncounters" + indices, filter);
        } else {
            filters.remove("relicInEncounters" + indices);
        }
    }

    // --------------------------------------------------------------------------------

    public static void setNthCardReward(String searchCard, int combatIndex){
        setNthCardReward(Collections.singletonList(searchCard), combatIndex);
    }

    public static void setNthCardReward(List<String> searchCards, int combatIndex){
        if (searchCards != null && !searchCards.isEmpty()) {
            NthCardRewardFilter filter = new NthCardRewardFilter(searchCards, combatIndex);
            filters.put("nthCardRewardFilter" + combatIndex, filter);
        } else {
            filters.remove("nthCardRewardFilter" + combatIndex);
        }
    }

    // --------------------------------------------------------------------------------

    public static void setBlessingCardFilter(NeowReward.NeowRewardType blessing, HashMap<String, Integer> cards, NeowReward.NeowRewardDrawback drawback){
        if (blessing == NeowReward.NeowRewardType.RANDOM_COLORLESS_2 || blessing == NeowReward.NeowRewardType.THREE_SMALL_POTIONS) {
            preRngCounters.put(SeedHelper.RNGType.CARD, 3);
        }
        BlessingFilter filter = new BlessingFilter(blessing, cards, drawback);
        filters.put("blessingFilter", filter);
    }

    public static void setBlessingRelicFilter(NeowReward.NeowRewardType blessing, String relicId, NeowReward.NeowRewardDrawback drawback){
       if (blessing == NeowReward.NeowRewardType.ONE_RARE_RELIC) {
            neowBonusRelicsCount.put(AbstractRelic.RelicTier.RARE, 1);
        } else if (blessing == NeowReward.NeowRewardType.RANDOM_COMMON_RELIC) {
            neowBonusRelicsCount.put(AbstractRelic.RelicTier.COMMON, 1);
        }
        BlessingFilter filter = new BlessingFilter(blessing, relicId, drawback);
        filters.put("blessingFilter", filter);
    }

    // --------------------------------------------------------------------------------

    public static void setCallingBellFilter(String commonRelic, String uncommonRelic, String rareRelic){
        CallingBellFilter filter = new CallingBellFilter(commonRelic, uncommonRelic, rareRelic);
        neowBonusRelicsCount.put(AbstractRelic.RelicTier.COMMON, 1);
        neowBonusRelicsCount.put(AbstractRelic.RelicTier.UNCOMMON, 1);
        neowBonusRelicsCount.put(AbstractRelic.RelicTier.RARE, 1);
        filters.put("callingBellFilter", filter);
    }

    public static void print() {
        System.out.println("FilterManager has " + filters.size() + " filters");
    }
}
