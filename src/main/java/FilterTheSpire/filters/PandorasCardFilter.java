package FilterTheSpire.filters;

import FilterTheSpire.simulators.MonsterRngSimulator;
import FilterTheSpire.simulators.PandorasStartingCardsSimulator;

public class PandorasCardFilter extends AbstractFilter {
    @Override
    public boolean isSeedValid(long seed) {
        return new PandorasStartingCardsSimulator(seed).isOk();
    }
}
