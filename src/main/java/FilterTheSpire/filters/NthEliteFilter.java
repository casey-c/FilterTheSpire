package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

public class NthEliteFilter extends AbstractFilter {
    private String eliteName;
    private int encounterIndex;

    public NthEliteFilter(String eliteName) {
        this.eliteName = eliteName;
        this.encounterIndex = 0; // Get the first elite if no index is specified.
    }

    public NthEliteFilter(String eliteName, int encounterIndex) {
        this.eliteName = eliteName;
        this.encounterIndex = encounterIndex;
    }

    public boolean isSeedValid(long seed) {
        return new MonsterRngSimulator(seed).nthElite(encounterIndex).equals(eliteName);
    }
}
