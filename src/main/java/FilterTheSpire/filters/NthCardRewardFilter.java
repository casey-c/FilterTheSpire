package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;

import java.util.HashMap;

public class NthCardRewardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private int combatIndex;

    public NthCardRewardFilter(HashMap<String, Integer> searchCards, int combatIndex){
        this.searchCards = searchCards;
        this.combatIndex = combatIndex;
    }

    public boolean isSeedValid(long seed) {
        return  CardRngSimulator.getInstance().getNthCardReward(seed, this.combatIndex, this.searchCards);
    }
}
