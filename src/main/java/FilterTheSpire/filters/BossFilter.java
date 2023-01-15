package FilterTheSpire.filters;

import FilterTheSpire.factory.MonsterListHelperFactory;
import FilterTheSpire.simulators.MonsterRngSimulator;
import FilterTheSpire.utils.FilterType;

import java.util.List;

public class BossFilter extends AbstractFilter {
    private List<String> bossNames;
    private int actNumber = 1;

    public BossFilter(List<String> bossNames)
    {
        type = FilterType.Boss;
        this.bossNames = bossNames;
    }

    public boolean isSeedValid(long seed) {
        String boss = MonsterRngSimulator.getInstance().firstBoss(seed, MonsterListHelperFactory.getMonsterListHelperFromActNumber(this.actNumber));
        return bossNames.contains(boss);
    }
}
