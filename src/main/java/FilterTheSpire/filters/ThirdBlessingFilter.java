package FilterTheSpire.filters;

import FilterTheSpire.simulators.BlessingSimulator;
import com.megacrit.cardcrawl.neow.NeowReward;

// Ideally, the blessing filter would be more generic, not just for the third blessing.
public class ThirdBlessingFilter extends AbstractFilter{
    private NeowReward.NeowRewardType rewardType;

    public ThirdBlessingFilter(NeowReward.NeowRewardType rewardType) {
        this.rewardType = rewardType;
    }

    public boolean isSeedValid(long seed) {
        return BlessingSimulator.getInstance().isThirdBlessingValid(seed, this.rewardType);
    }
}
