package FilterTheSpire.filters;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.simulators.RelicRngSimulator;
import FilterTheSpire.utils.config.FilterType;

import java.util.Collections;
import java.util.List;

public class NthRelicFilter extends AbstractFilter {
    private final List<String> relicNames;
    private final int encounterIndex;

    public NthRelicFilter(List<String> relicNames, int encounterIndex) {
        initialize();
        this.relicNames = relicNames;
        this.encounterIndex = encounterIndex;
    }

    private void initialize(){
        type = FilterType.NthRelic;
        displayName = "Relic Filter";
    }

    public boolean isSeedValid(long seed) {
        return RelicRngSimulator.getInstance().isNthRelicValid(this.relicNames, seed, this.encounterIndex);
    }

    public boolean shouldDisplay() {
        return relicNames.size() > 0;
    }

    public String toString() {
        if (shouldDisplay()){
            return relicNames + " at encounter " + (encounterIndex + 1);
        }
        return "";
    }

    public String generateHashKey() {
        return FilterTheSpire.config.generateHashKey(type, Collections.singletonList(encounterIndex));
    }
}
