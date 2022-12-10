package FilterTheSpire.factory;

import FilterTheSpire.filters.*;
import org.apache.commons.lang3.NotImplementedException;

public class FilterFactory {
    public static AbstractFilter getAbstractFilterFromFilterObject(FilterObject filterObject) {
        switch (filterObject.filterType) {
            case Boss:
                return new BossFilter(filterObject.anyOf);
            case NthBossRelic:
                return new NthBossRelicFilter(filterObject.anyOf, filterObject.possibleEncounterIndices);
            case NthColorlessRareCard:
                return new NthColorlessRareCardFilter(filterObject.anyOf);
            case NthCombat:
                return new NthCombatFilter(filterObject.anyOf);
            case NthElite:
                return new NthEliteFilter(filterObject.anyOf);
            case NthShopRelic:
                return new NthShopRelicFilter(filterObject.anyOf);
            case PandorasCard:
                return new PandorasCardFilter(filterObject.anyOf);
            case NeowBonus:
                return new BlessingFilter(filterObject.anyOf, filterObject.noneOf);
            default:
                throw new NotImplementedException("Unknown filter type");
        }
    }

    // Seems like we'd want a similar function to create Filter UI objects from a FilterObject as well
}
