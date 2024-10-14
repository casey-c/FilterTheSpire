package FilterTheSpire.filters;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.simulators.RelicRngSimulator;
import FilterTheSpire.utils.config.FilterType;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Collections;
import java.util.List;

public class NthShopRelicFilter extends AbstractFilter {
    private final List<String> shopRelicNames;
    private final int encounterIndex;

    public NthShopRelicFilter(List<String> shopRelicNames) {
        initialize();
        this.shopRelicNames = shopRelicNames;
        this.encounterIndex = 0; // Get the first shop relic if no index is specified.
    }

    public NthShopRelicFilter(List<String> shopRelicNames, int encounterIndex) {
        initialize();
        this.shopRelicNames = shopRelicNames;
        this.encounterIndex = encounterIndex;
    }

    private void initialize(){
        type = FilterType.NthShopRelic;
        displayName = "Shop Relic Filter";
    }

    public boolean isSeedValid(long seed) {
        List<String> shopRelicPool = RelicRngSimulator.getInstance().getRelicPools(seed, AbstractRelic.RelicTier.SHOP);
        Collections.reverse(shopRelicPool); // Shop relics are done in reverse order
        return shopRelicNames.contains(shopRelicPool.get(this.encounterIndex));
    }

    public boolean shouldDisplay() {
        return shopRelicNames.size() > 0;
    }

    public String toString() {
        if (shopRelicNames.size()> 0){
            return shopRelicNames + " at encounter " + (encounterIndex + 1);
        }
        return "";
    }

    public String generateHashKey() {
        return FilterTheSpire.config.generateHashKey(type, Collections.singletonList(encounterIndex));
    }
}
