package FilterTheSpire.factory;

import FilterTheSpire.filters.*;
import org.apache.commons.lang3.NotImplementedException;

public class FilterFactory {
    public static AbstractFilter getAbstractFilterFromFilterObject(FilterObject filterObject) {
        switch (filterObject.filterType) {
            case Boss:
                return new BossFilter(filterObject.possibleValues);
            case NthBossRelic:
                return new NthBossRelicFilter(filterObject.possibleValues, filterObject.possibleEncounterIndices);
            case NthColorlessRareCard:
                return new NthColorlessRareCardFilter(filterObject.possibleValues);
            case NthCombat:
                return new NthCombatFilter(filterObject.possibleValues);
            case NthElite:
                return new NthEliteFilter(filterObject.possibleValues);
            case NthShopRelic:
                return new NthShopRelicFilter(filterObject.possibleValues);
            case PandorasCard:
                return new PandorasCardFilter(filterObject.possibleValues);
            case NeowBonus:
                return new BlessingFilter(filterObject.possibleValues, filterObject.secondaryValues, filterObject.searchCards);
            case NthRelic:
                return new NthRelicFilter(filterObject.possibleValues, filterObject.possibleEncounterIndices.get(0));
            case NthCardReward:
                return new NthCardRewardFilter(filterObject.possibleValues, filterObject.possibleEncounterIndices.get(0));
            default:
                throw new NotImplementedException("Unknown filter type");
        }
    }

    // Seems like we'd want a similar function to create Filter UI objects from a FilterObject as well
}
