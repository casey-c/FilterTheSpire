package FilterTheSpire.filters;

import FilterTheSpire.simulators.ShopRelicRngSimulator;

public class NthShopRelicFilter extends AbstractFilter {
    private String shopRelicName;
    private int encounterIndex;
    private ShopRelicRngSimulator shopRelicRngSimulator;

    public NthShopRelicFilter(String shopRelicName) {
        this.shopRelicName = shopRelicName;
        this.encounterIndex = 0; // Get the first shop relic if no index is specified.
        shopRelicRngSimulator = new ShopRelicRngSimulator();
    }

    public boolean isSeedValid(long seed) {
        shopRelicRngSimulator.rerollRelics(seed);
        return shopRelicRngSimulator.nthShopRelic(encounterIndex).equals(shopRelicName);
    }
}
