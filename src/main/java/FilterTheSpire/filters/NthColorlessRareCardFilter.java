package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;

public class NthColorlessRareCardFilter extends AbstractFilter{
    private String cardName;
    private int encounterNumber;

    public NthColorlessRareCardFilter(String cardName) {
        this.cardName = cardName;
        this.encounterNumber = 1; // Get the first card if no index is specified.
    }

    public NthColorlessRareCardFilter(String cardName, int encounterNumber) {
        this.cardName = cardName;
        this.encounterNumber = encounterNumber;
    }

    public boolean isSeedValid(long seed) {
        return new CardRngSimulator(seed).nthColorlessRareCard(encounterNumber).equals(cardName);
    }
}
