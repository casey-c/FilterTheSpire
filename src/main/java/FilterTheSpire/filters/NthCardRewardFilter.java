package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;
import FilterTheSpire.utils.config.FilterType;

import java.util.List;

public class NthCardRewardFilter extends AbstractFilter {
    private List<String> searchCards;
    private int combatIndex;

    public NthCardRewardFilter(List<String> searchCards, int combatIndex){
        type = FilterType.NthCardReward;
        this.searchCards = searchCards;
        this.combatIndex = combatIndex;
    }

    public boolean isSeedValid(long seed) {
        return CardRngSimulator.getInstance().isNthCardRewardValid(seed, this.combatIndex, this.searchCards);
    }
}
