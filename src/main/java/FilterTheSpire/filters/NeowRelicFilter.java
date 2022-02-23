package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;

import java.util.List;

public class NeowRelicFilter extends AbstractFilter {
    private List<String> relicNames;
    private int encounterIndex;

    public NeowRelicFilter(List<String> relicNames, int encounterIndex) {
        this.relicNames = relicNames;
        this.encounterIndex = encounterIndex;
    }

    public boolean isSeedValid(long seed) {
        return RelicRngSimulator.getInstance().isNthRelicValid(this.relicNames, seed, this.encounterIndex);
    }
}
