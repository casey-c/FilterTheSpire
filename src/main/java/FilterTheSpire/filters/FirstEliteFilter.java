package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;

public class FirstEliteFilter extends AbstractFilter {
    private String eliteName;

    public FirstEliteFilter(String eliteName) {
        this.eliteName = eliteName;
    }

    public boolean isSeedValid(long seed) {
        return new MonsterRngSimulator(seed).isFirstElite(eliteName);
    }
}
