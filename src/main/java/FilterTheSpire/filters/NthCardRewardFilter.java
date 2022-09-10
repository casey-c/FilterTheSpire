package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;

import java.util.List;

public class NthCardRewardFilter extends AbstractFilter {
    private List<String> searchCards;
    private int combatIndex;

    public NthCardRewardFilter(List<String> searchCards, int combatIndex){
        this.searchCards = searchCards;
        this.combatIndex = combatIndex;
    }

    public boolean isSeedValid(long seed) {
        return  CardRngSimulator.getInstance().getNthCardReward(seed, this.combatIndex, this.searchCards);
    }
}
