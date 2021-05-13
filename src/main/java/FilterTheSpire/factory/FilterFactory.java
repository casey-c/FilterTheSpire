package FilterTheSpire.factory;

import FilterTheSpire.filters.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FilterFactory {
    public static AbstractFilter getAbstractFilterFromFilterObject(FilterObject filterObject) {
        switch (filterObject.filterType) {
            case Boss:
                return new BossFilter(filterObject.anyOf);
            case NthBossRelic:
                return new NthBossRelicFilter(filterObject.anyOf);
            case NthColorlessRareCard:
                return new NthColorlessRareCardFilter(filterObject.anyOf);
            case NthCombat:
                return new NthCombatFilter(filterObject.anyOf);
            case NthElite:
                return new NthEliteFilter(filterObject.anyOf);
            case NthShopRelic:
                return new NthShopRelicFilter(filterObject.anyOf);
            case PandorasCard:
                // Since this is hardcoded to look for 6 blade dances, lets not even risk creating this yet
                throw new NotImplementedException();
            default:
                throw new NotImplementedException();
        }
    }

    // Seems like we'd want a similar function to create Filter UI objects from a FilterObject as well
}
