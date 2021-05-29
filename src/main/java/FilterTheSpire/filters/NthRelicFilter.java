package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;

import java.util.List;

// This doesn't actually work yet since there's so many ways to change the overall relic pool
// It does work for if the Neow Bonus would be Random Common relic or Random Rare relic though
// since those are guaranteed the first relics, so for now, it should only be used for those
public class NthRelicFilter extends AbstractFilter {
    private List<String> relicNames;
    private int encounterIndex;

    public NthRelicFilter(List<String> relicNames) {
        this.relicNames = relicNames;
        this.encounterIndex = 0; // Get the first Relic
    }

    public boolean isSeedValid(long seed) {
        return RelicRngSimulator.getInstance().isNthRelicValid(this.relicNames, seed, this.encounterIndex);
    }
}
