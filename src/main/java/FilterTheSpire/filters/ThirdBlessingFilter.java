package FilterTheSpire.filters;

import FilterTheSpire.simulators.BlessingSimulator;

// Ideally, the blessing filter would be more generic, not just for the third blessing.
public class ThirdBlessingFilter extends AbstractFilter{
    public ThirdBlessingFilter() { }

    public boolean isSeedValid(long seed) {
        // For now, only check if the third blessing is a colorless card.
        return new BlessingSimulator(seed).isThirdBlessingColorlessCard();
    }
}
