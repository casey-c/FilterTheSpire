package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;

public class NthBossRelicFilter extends AbstractFilter{
    private String bossRelicName;
    private int encounterIndex;

    public NthBossRelicFilter(String bossRelicName) {
        this.bossRelicName = bossRelicName;
        this.encounterIndex = 0; // Get the first elite encounter if no index is specified.
    }

    public NthBossRelicFilter(String bossRelicName, int encounterIndex) {
        this.bossRelicName = bossRelicName;
        this.encounterIndex = encounterIndex;
    }

    public boolean isSeedValid(long seed) {
        return new RelicRngSimulator(seed).nthBossSwap(encounterIndex).equals(bossRelicName);
    }
}
