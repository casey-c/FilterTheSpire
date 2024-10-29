package FilterTheSpire;

import FilterTheSpire.factory.FilterFactory;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.filters.*;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.helpers.SeedHelper;
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

    private static final HashMap<String, AbstractFilter> filters = new HashMap<>();
    private static List<AbstractFilter> sortedList = null;

    // Returns true if all filters pass for the given seed
    public static boolean validateFilters(long seed) {
        return sortedList.stream().allMatch(v -> v.isSeedValid(seed));
    }

    // Sort the filters, so we short circuit on the least intensive filters first
    public static void sortFilters(){
        sortedList = new ArrayList<>(filters.values());
        sortedList.sort(Comparator.comparingInt(AbstractFilter::getSortOrder));
    }

    public static boolean hasFilters() {
        return filters.size() > 0;
    }

    public static ArrayList<AbstractFilter> getFilters() {
        return new ArrayList<>(filters.values());
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

    public static void clearFilters(){
        filters.clear();
        preRngCounters.clear();
    }

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

    // REMOVE setFilter when all filters are Adding and not updating real time
    public static void addFilter(FilterObject filterObject){
        if (isValidFilter(filterObject)){
            AbstractFilter filter = FilterFactory.getAbstractFilterFromFilterObject(filterObject);
            filters.put(filterObject.getHashKey(), filter);
        }
        setPreRunCounters();
    }

    private static void setPreRunCounters(){
        preRngCounters.clear();
        for (String key: filters.keySet()) {
            AbstractFilter filter = filters.get(key);
            if (filter.type == FilterType.NeowBonus) {
                BlessingFilter f = (BlessingFilter) filter;
                if (f.rewardType == NeowReward.NeowRewardType.RANDOM_COLORLESS_2 ||
                        f.rewardType == NeowReward.NeowRewardType.RANDOM_COLORLESS) {
                    preRngCounters.put(SeedHelper.RNGType.CARD, 3);
                } else if (f.rewardType == NeowReward.NeowRewardType.THREE_SMALL_POTIONS) {
                    preRngCounters.put(SeedHelper.RNGType.CARD, 9);
                }
            }
        }
    }

    public static void loadInitialFilters(){
        filters.clear();
        for (FilterObject filterObject : FilterTheSpire.config.getActiveFilters()) {
            if (isValidFilter(filterObject)) {
                filters.put(filterObject.getHashKey(), FilterFactory.getAbstractFilterFromFilterObject(filterObject));
            }
        }
    }

    private static boolean isValidFilter(FilterObject filterObject){
        return filterObject.possibleValues.size() > 0 ||
                filterObject.secondaryValues.size() > 0 ||
                filterObject.searchCards.size() > 0;
    }
}
