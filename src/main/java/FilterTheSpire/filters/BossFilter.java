package FilterTheSpire.filters;

import FilterTheSpire.factory.MonsterListHelperFactory;
import FilterTheSpire.simulators.MonsterRngSimulator;
import FilterTheSpire.utils.config.FilterType;

import java.util.List;

public class BossFilter extends AbstractFilter {
    private final List<String> bossNames;
    private int actNumber = 1;

    public BossFilter(List<String> bossNames) {
        this(bossNames, 1);
    }

    public BossFilter(List<String> bossNames, int actNumber){
        type = FilterType.Boss;
        this.bossNames = bossNames;
        this.actNumber = actNumber;
    }

    public boolean isSeedValid(long seed) {
        String boss = MonsterRngSimulator.getInstance().firstBoss(seed, MonsterListHelperFactory.getMonsterListHelperFromActNumber(this.actNumber));
        return bossNames.contains(boss);
    }

    public String generateHashKey() {
        return null;
    }
}
