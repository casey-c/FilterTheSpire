package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

public class BossFilter extends AbstractFilter {
    private String bossName;

    public BossFilter(String bossName) {
        this.bossName = bossName;
    }

    public boolean isSeedValid(long seed) {
        return new MonsterRngSimulator(seed).firstBoss().equals(bossName);
    }
}
