package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

import java.util.List;

public class NthEliteFilter extends AbstractFilter {
    private List<String> eliteNames;
    private int encounterIndex;

    public NthEliteFilter(List<String> eliteNames) {
        this.eliteNames = eliteNames;
        this.encounterIndex = 0; // Get the first elite if no index is specified.
    }

    public NthEliteFilter(List<String> eliteNames, int encounterIndex) {
        this.eliteNames = eliteNames;
        this.encounterIndex = encounterIndex;
    }

    public boolean isSeedValid(long seed) {
        String eliteId = new MonsterRngSimulator(seed).nthElite(encounterIndex);
        return eliteNames.contains(eliteId);
    }
}
