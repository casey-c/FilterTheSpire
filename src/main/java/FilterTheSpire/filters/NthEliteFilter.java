package FilterTheSpire.filters;

import FilterTheSpire.factory.MonsterListHelperFactory;
import FilterTheSpire.simulators.MonsterRngSimulator;
import FilterTheSpire.utils.config.FilterType;

import java.util.List;

public class NthEliteFilter extends AbstractFilter {
    private final List<String> eliteNames;
    private final int encounterIndex;
    private final int actNumber = 1;

    public NthEliteFilter(List<String> eliteNames) {
        type = FilterType.NthElite;
        this.eliteNames = eliteNames;
        this.encounterIndex = 0; // Get the first elite if no index is specified.
    }

    public NthEliteFilter(List<String> eliteNames, int encounterIndex) {
        this.eliteNames = eliteNames;
        this.encounterIndex = encounterIndex;
    }

    public boolean isSeedValid(long seed) {
        String eliteId = MonsterRngSimulator.getInstance().nthElite(seed, encounterIndex, MonsterListHelperFactory.getMonsterListHelperFromActNumber(this.actNumber));
        return eliteNames.contains(eliteId);
    }

    public String generateHashKey() {
        return null;
    }
}
