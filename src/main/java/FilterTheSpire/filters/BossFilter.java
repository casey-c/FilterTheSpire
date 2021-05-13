package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

import java.util.List;

public class BossFilter extends AbstractFilter {
    private List<String> bossNames;
    private int actNumber;

    public BossFilter(List<String> bossNames) {
        this.bossNames = bossNames;
    }

    public boolean isSeedValid(long seed) {
        String boss = new MonsterRngSimulator(seed).firstBoss();
        return bossNames.contains(boss);
    }
}
