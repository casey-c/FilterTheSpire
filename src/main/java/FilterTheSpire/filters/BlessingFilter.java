package FilterTheSpire.filters;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.simulators.BlessingSimulator;
import FilterTheSpire.utils.config.FilterType;
import com.megacrit.cardcrawl.neow.NeowReward;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BlessingFilter extends AbstractFilter {
    public NeowReward.NeowRewardType rewardType;
    private final HashMap<String, Integer> searchCards;
    private NeowReward.NeowRewardDrawback drawback = null;

    public BlessingFilter(List<String> rewardType, List<String> drawback, HashMap<String, Integer> searchCards) {
        initialize();
        if (rewardType != null && rewardType.size() > 0){
            this.rewardType = NeowReward.NeowRewardType.valueOf(rewardType.get(0));
        }
        if (drawback != null && drawback.size() > 0){
            this.drawback = NeowReward.NeowRewardDrawback.valueOf(drawback.get(0));
        }
        this.searchCards = searchCards;
    }

    public BlessingFilter(NeowReward.NeowRewardType rewardType, NeowReward.NeowRewardDrawback drawback, HashMap<String, Integer> searchCards) {
        initialize();
        this.rewardType = rewardType;
        this.drawback = drawback;
        this.searchCards = searchCards;
    }

    private void initialize(){
        type = FilterType.NeowBonus;
        displayName = "Neow Bonus Filter";
    }

    public boolean isSeedValid(long seed) {
        if (this.rewardType == null && this.drawback == null){
            return true;
        }
        return BlessingSimulator.getInstance().isBlessingValid(seed, this.rewardType, searchCards, this.drawback);
    }

    public boolean shouldDisplay() {
        return rewardType != null;
    }

    public String toString() {
        return "reward type = " + rewardType +
                ", searchCards = " + searchCards +
                ", drawback = " + drawback;
    }

    public String generateHashKey() {
        return FilterTheSpire.config.generateHashKey(type, Collections.singletonList(0));
    }
}
