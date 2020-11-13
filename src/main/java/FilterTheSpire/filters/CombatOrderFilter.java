package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

import java.util.ArrayList;

public class CombatOrderFilter extends AbstractFilter {
    private ArrayList<String> enemyNames;

    public CombatOrderFilter(ArrayList<String> enemyNames) {
        this.enemyNames = enemyNames;
    }

    public boolean isSeedValid(long seed) {
        MonsterRngSimulator monsterRngSimulator = new MonsterRngSimulator(seed);
        for (int i = 0; i < enemyNames.size(); i++) {
            if (monsterRngSimulator.nthCombatIs(i) != enemyNames.get(i)) {
                return false;
            }
        }
        return true;
    }
}
