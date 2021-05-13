package FilterTheSpire.filters;

import FilterTheSpire.simulators.ShopRelicRngSimulator;

import java.util.List;

public class NthShopRelicFilter extends AbstractFilter {
    private List<String> shopRelicNames;
    private int encounterIndex;
    private ShopRelicRngSimulator shopRelicRngSimulator;

    public NthShopRelicFilter(List<String> shopRelicNames) {
        this.shopRelicNames = shopRelicNames;
        this.encounterIndex = 0; // Get the first shop relic if no index is specified.
        shopRelicRngSimulator = new ShopRelicRngSimulator();
    }

    public boolean isSeedValid(long seed) {
        shopRelicRngSimulator.rerollRelics(seed);
        String shopRelicId = shopRelicRngSimulator.nthShopRelic(encounterIndex);
        return shopRelicNames.contains(shopRelicId);
    }
}
