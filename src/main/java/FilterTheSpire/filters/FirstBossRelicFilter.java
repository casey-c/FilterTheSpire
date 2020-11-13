package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;

public class FirstBossRelicFilter extends AbstractFilter{
    private String target;

    public FirstBossRelicFilter(String target) {
        this.target = target;
    }

    public boolean isSeedValid(long seed) {
        return RelicRngSimulator.isBossSwap(seed, target);
    }
}
