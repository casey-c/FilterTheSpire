package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

import java.util.List;

public class NthCombatFilter extends AbstractFilter {
    private List<String> enemyNames;
    private int encounterNumber;

    public NthCombatFilter(List<String> enemyNames) {
        this.enemyNames = enemyNames;
        this.encounterNumber = 0; // Get the first combat if no index is specified.
    }

    public NthCombatFilter(List<String> enemyNames, int encounterNumber) {
        this.enemyNames = enemyNames;
        this.encounterNumber = encounterNumber;
    }

    public boolean isSeedValid(long seed) {
        String enemyId = new MonsterRngSimulator(seed).nthCombat(encounterNumber);
        return enemyNames.contains(enemyId);
    }
}
