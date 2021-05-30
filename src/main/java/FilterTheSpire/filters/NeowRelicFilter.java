package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;

import java.util.List;

public class NeowRelicFilter extends AbstractFilter {
    private List<String> relicNames;
    private int encounterIndex;

    public NeowRelicFilter(List<String> relicNames) {
        this.relicNames = relicNames;
        this.encounterIndex = 0; // Get the first Relic
    }

    public boolean isSeedValid(long seed) {
        return RelicRngSimulator.getInstance().isNthRelicValid(this.relicNames, seed, this.encounterIndex);
    }
}
