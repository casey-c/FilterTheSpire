package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;

public class FirstColorlessRareFilter extends AbstractFilter{
    private String target;

    public FirstColorlessRareFilter(String target) {
        this.target = target;
    }

    public boolean isSeedValid(long seed) {
        String firstColorlessRare = new CardRngSimulator(seed).firstColorlessRareCardIs();
        return firstColorlessRare == target;
    }
}
