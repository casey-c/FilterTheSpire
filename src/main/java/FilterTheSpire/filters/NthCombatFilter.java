package FilterTheSpire.filters;

import FilterTheSpire.factory.MonsterListHelperFactory;
import FilterTheSpire.simulators.MonsterRngSimulator;
import FilterTheSpire.utils.config.FilterType;

import java.util.List;

public class NthCombatFilter extends AbstractFilter {
    private List<String> enemyNames;
    private int encounterNumber;
    private int actNumber = 1;

    public NthCombatFilter(List<String> enemyNames) {
        type = FilterType.NthCombat;
        this.enemyNames = enemyNames;
        this.encounterNumber = 0; // Get the first combat if no index is specified.
    }

    public NthCombatFilter(List<String> enemyNames, int encounterNumber) {
        this.enemyNames = enemyNames;
        this.encounterNumber = encounterNumber;
    }

    public boolean isSeedValid(long seed) {
        String enemyId = MonsterRngSimulator.getInstance().nthCombat(seed, encounterNumber, MonsterListHelperFactory.getMonsterListHelperFromActNumber(this.actNumber));
        return enemyNames.contains(enemyId);
    }
}
