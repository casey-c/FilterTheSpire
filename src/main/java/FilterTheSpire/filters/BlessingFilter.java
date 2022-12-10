package FilterTheSpire.filters;

import FilterTheSpire.simulators.BlessingSimulator;
import com.megacrit.cardcrawl.neow.NeowReward;

import java.util.HashMap;
import java.util.List;

// The searchCards stuff doesn't work at all right now. Thought we could directly set the resulting Transform cards easily
public class BlessingFilter extends AbstractFilter {
    private NeowReward.NeowRewardType rewardType;
    private HashMap<String, Integer> searchCards;
    private NeowReward.NeowRewardDrawback drawback = null;

    public BlessingFilter(List<String> rewardType, List<String> drawback) {
        if (rewardType != null && rewardType.size() > 0){
            this.rewardType = NeowReward.NeowRewardType.valueOf(rewardType.get(0));
        }
        if (drawback != null && drawback.size() > 0){
            this.drawback = NeowReward.NeowRewardDrawback.valueOf(drawback.get(0));
        }
        this.searchCards = new HashMap<>();
    }

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
        if (this.rewardType == null && this.drawback == null){
            return true;
        }
        return BlessingSimulator.getInstance().isBlessingValid(seed, this.rewardType, searchCards, this.drawback);
    }
}
