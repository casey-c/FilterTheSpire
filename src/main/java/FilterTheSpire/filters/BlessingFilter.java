package FilterTheSpire.filters;

import FilterTheSpire.simulators.BlessingSimulator;
import com.megacrit.cardcrawl.neow.NeowReward;

import java.util.HashMap;

// The searchCards stuff doesn't work at all right now. Thought we could directly set the resulting Transform cards easily
public class BlessingFilter extends AbstractFilter{
    private NeowReward.NeowRewardType rewardType;
    private HashMap<String, Integer> searchCards;
    private NeowReward.NeowRewardDrawback drawback = null;

    public BlessingFilter(NeowReward.NeowRewardType rewardType) {
        this.rewardType = rewardType;
        this.searchCards = new HashMap<>();
    }

    public BlessingFilter(NeowReward.NeowRewardType rewardType, NeowReward.NeowRewardDrawback drawback) {
        this.rewardType = rewardType;
        this.searchCards = new HashMap<>();
        this.drawback = drawback;
    }

    public BlessingFilter(NeowReward.NeowRewardType rewardType, HashMap<String, Integer> searchCards) {
        this.rewardType = rewardType;
        this.searchCards = searchCards;
    }

    public BlessingFilter(NeowReward.NeowRewardType rewardType, HashMap<String, Integer> searchCards, NeowReward.NeowRewardDrawback drawback) {
        this.rewardType = rewardType;
        this.searchCards = searchCards;
        this.drawback = drawback;
    }

    public boolean isSeedValid(long seed) {
        return BlessingSimulator.getInstance().isBlessingValid(seed, this.rewardType, searchCards, this.drawback);
    }
}
