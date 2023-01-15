package FilterTheSpire;

import FilterTheSpire.factory.FilterFactory;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.filters.*;
import FilterTheSpire.utils.FilterType;
import FilterTheSpire.utils.SeedHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.relics.AbstractRelic;

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

    public static void setFilter(FilterObject filterObject){
        if (filterObject.possibleValues.size() > 0 || filterObject.secondaryValues.size() > 0){
            AbstractFilter filter = FilterFactory.getAbstractFilterFromFilterObject(filterObject);
            if (filterObject.isKeyUpdated()){
                filters.remove(filterObject.getOldHashKey());
                filterObject.updateHashKey();
            }
            filters.put(filterObject.getHashKey(), filter);
        } else {
            filters.remove(filterObject.getHashKey());
        }
        setPreRunCounters();
    }

    private static void setPreRunCounters(){
        preRngCounters.clear();
        for (String key: filters.keySet()) {
            AbstractFilter filter = filters.get(key);
            if (filter.type == FilterType.NeowBonus) {
                BlessingFilter f = (BlessingFilter) filter;
                if (f.rewardType == NeowReward.NeowRewardType.RANDOM_COLORLESS_2) {
                    preRngCounters.put(SeedHelper.RNGType.CARD, 3);
                } else if (f.rewardType == NeowReward.NeowRewardType.THREE_SMALL_POTIONS) {
                    preRngCounters.put(SeedHelper.RNGType.CARD, 9);
                }
            }
        }
    }

    public static void setPandorasCardFilter(HashMap<String, Integer> searchCards) {
        filters.put("pandorasTmp", new PandorasCardFilter(searchCards));
    }

    public static void setAstrolabeCardFilter(HashMap<String, Integer> searchCards) {
        filters.put("astrolabeFilter", new AstrolabeCardFilter(searchCards));
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
