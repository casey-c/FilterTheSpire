package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

public class FirstBossFilter extends AbstractFilter {
    private String bossName;

    public FirstBossFilter(String bossName) {
        this.bossName = bossName;
    }

    public boolean isSeedValid(long seed) {
        return new MonsterRngSimulator(seed).isFirstBoss(bossName);
    }
}
