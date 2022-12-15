package FilterTheSpire.filters;

import FilterTheSpire.simulators.BlessingSimulator;
import com.megacrit.cardcrawl.neow.NeowReward;

import java.util.HashMap;
import java.util.List;

public class BlessingFilter extends AbstractFilter {
    private NeowReward.NeowRewardType rewardType;
    private HashMap<String, Integer> searchCards;
    private NeowReward.NeowRewardDrawback drawback = null;
    private String relicId = null;

    public BlessingFilter(List<String> rewardType, List<String> drawback, HashMap<String, Integer> searchCards) {
        if (rewardType != null && rewardType.size() > 0){
            this.rewardType = NeowReward.NeowRewardType.valueOf(rewardType.get(0));
        }
        if (drawback != null && drawback.size() > 0){
            this.drawback = NeowReward.NeowRewardDrawback.valueOf(drawback.get(0));
        }
        this.searchCards = searchCards;
    }

    public boolean isSeedValid(long seed) {
        if (this.rewardType == null && this.drawback == null){
            return true;
        }
        return BlessingSimulator.getInstance().isBlessingValid(seed, this.rewardType, searchCards, relicId, this.drawback);
    }
}
