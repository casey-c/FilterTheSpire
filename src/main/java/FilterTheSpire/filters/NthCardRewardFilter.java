package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;

public class NthCardRewardFilter extends AbstractFilter {
    private String searchCard;
    private int combatIndex;

    public NthCardRewardFilter(String searchCard, int combatIndex){
        this.searchCard = searchCard;
        this.combatIndex = combatIndex;
    }

    public boolean isSeedValid(long seed) {
        return  CardRngSimulator.getInstance().getNthCardReward(seed, this.combatIndex, this.searchCard);
    }
}
