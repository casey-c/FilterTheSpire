package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;

import java.util.List;

public class NthColorlessRareCardFilter extends AbstractFilter{
    private List<String> cardNames;
    private int encounterNumber;

    public NthColorlessRareCardFilter(List<String> cardNames) {
        this.cardNames = cardNames;
        this.encounterNumber = 1; // Get the first card if no index is specified.
    }

    public NthColorlessRareCardFilter(List<String> cardNames, int encounterNumber) {
        this.cardNames = cardNames;
        this.encounterNumber = encounterNumber;
    }

    public boolean isSeedValid(long seed) {
        return CardRngSimulator.getInstance().isValidColorlessRareCardFromNeow(seed, cardNames);
    }
}
