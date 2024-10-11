package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.HashMap;
import java.util.List;

public class CallingBellFilter extends AbstractFilter {
    private final String commonRelic;
    private final String uncommonRelic;
    private final String rareRelic;

    public CallingBellFilter(String commonRelic, String uncommonRelic, String rareRelic) {
        this.commonRelic = commonRelic;
        this.uncommonRelic = uncommonRelic;
        this.rareRelic = rareRelic;
    }

    public boolean isSeedValid(long seed) {
        HashMap<AbstractRelic.RelicTier, List<String>> relicPools = RelicRngSimulator.getInstance().getRelicPools(seed);
        boolean hasCommonRelic = relicPools.get(AbstractRelic.RelicTier.COMMON).get(0).equals(commonRelic);
        boolean hasUncommonRelic = relicPools.get(AbstractRelic.RelicTier.UNCOMMON).get(0).equals(uncommonRelic);
        boolean hasRareRelic = relicPools.get(AbstractRelic.RelicTier.RARE).get(0).equals(rareRelic);
        return hasCommonRelic && hasUncommonRelic && hasRareRelic;
    }

    public String generateHashKey() {
        return null;
    }
}
