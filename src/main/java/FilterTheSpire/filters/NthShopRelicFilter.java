package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;
import FilterTheSpire.utils.FilterType;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Collections;
import java.util.List;

public class NthShopRelicFilter extends AbstractFilter {
    private List<String> shopRelicNames;
    private int encounterIndex;

    public NthShopRelicFilter(List<String> shopRelicNames) {
        type = FilterType.NthShopRelic;
        this.shopRelicNames = shopRelicNames;
        this.encounterIndex = 0; // Get the first shop relic if no index is specified.
    }

    public NthShopRelicFilter(List<String> shopRelicNames, int encounterIndex) {
        this.shopRelicNames = shopRelicNames;
        this.encounterIndex = encounterIndex;
    }

    public boolean isSeedValid(long seed) {
        List<String> shopRelicPool = RelicRngSimulator.getInstance().getRelicPools(seed, AbstractRelic.RelicTier.SHOP);
        Collections.reverse(shopRelicPool); // Shop relics are done in reverse order
        return shopRelicNames.contains(shopRelicPool.get(this.encounterIndex));
    }
}
