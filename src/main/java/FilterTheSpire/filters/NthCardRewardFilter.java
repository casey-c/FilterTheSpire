package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;
import FilterTheSpire.utils.config.FilterType;

import java.util.List;

public class NthCardRewardFilter extends AbstractFilter {
    private final List<String> searchCards;
    private final int combatIndex;

    public NthCardRewardFilter(List<String> searchCards, int combatIndex){
        initialize();
        this.searchCards = searchCards;
        this.combatIndex = combatIndex;
    }

    private void initialize(){
        type = FilterType.NthCardReward;
        displayName = "Card Reward Filter";
    }

    public boolean isSeedValid(long seed) {
        return CardRngSimulator.getInstance().isNthCardRewardValid(seed, this.combatIndex, this.searchCards);
    }

    public boolean shouldDisplay() {
        return searchCards.size() > 0;
    }

    public String toString() {
        return "Cards: " + searchCards + ", combat Index: " + combatIndex;
    }
}
