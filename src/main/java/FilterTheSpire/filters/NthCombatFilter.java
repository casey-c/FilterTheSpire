package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

public class NthCombatFilter extends AbstractFilter {
    private String enemyName;
    private int encounterNumber;

    public NthCombatFilter(String enemyName) {
        this.enemyName = enemyName;
        this.encounterNumber = 0; // Get the first combat if no index is specified.
    }

    public NthCombatFilter(String enemyName, int encounterNumber) {
        this.enemyName = enemyName;
        this.encounterNumber = encounterNumber;
    }

    public boolean isSeedValid(long seed) {
        return new MonsterRngSimulator(seed).nthCombat(encounterNumber).equals(enemyName);
    }
}
