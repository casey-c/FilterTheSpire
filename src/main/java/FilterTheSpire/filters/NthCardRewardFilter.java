package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardRngSimulator;

import java.util.HashMap;
import java.util.List;

public class NthCardRewardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private List<Integer> encounterIndices;

    public NthCardRewardFilter(HashMap<String, Integer> searchCards, List<Integer> encounterIndices){
        this.searchCards = searchCards;
        this.encounterIndices = encounterIndices;
    }

    public boolean isSeedValid(long seed) {
        return  CardRngSimulator.getInstance().getNthCardReward(seed, this.encounterIndices, this.searchCards);
    }
}
