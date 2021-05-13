package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;

import java.util.List;

public class NthBossRelicFilter extends AbstractFilter{
    private List<String> bossRelicNames;
    private int encounterIndex;
    private RelicRngSimulator relicRngSimulator;

    public NthBossRelicFilter(List<String> bossRelicNames) {
        this.bossRelicNames = bossRelicNames;
        this.encounterIndex = 0; // Get the first elite encounter if no index is specified.
        this.relicRngSimulator = new RelicRngSimulator();
    }

    public NthBossRelicFilter(List<String> bossRelicNames, int encounterIndex) {
        this.bossRelicNames = bossRelicNames;
        this.encounterIndex = encounterIndex;
        this.relicRngSimulator = new RelicRngSimulator();
    }

    public boolean isSeedValid(long seed) {
        relicRngSimulator.setSeed(seed);
        String seedBossRelic = relicRngSimulator.nthBossSwap(encounterIndex);
        return bossRelicNames.contains(seedBossRelic);
    }
}
