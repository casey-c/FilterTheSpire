package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.List;

public class NthBossRelicFilter extends AbstractFilter{
    private List<String> bossRelicNames;
    private int encounterIndex;

    public NthBossRelicFilter(List<String> bossRelicNames) {
        this.bossRelicNames = bossRelicNames;
        this.encounterIndex = 0; // Get the first Boss Relic if no index is specified.
    }

    public NthBossRelicFilter(List<String> bossRelicNames, int encounterIndex) {
        this.bossRelicNames = bossRelicNames;
        this.encounterIndex = encounterIndex;
    }

    public boolean isSeedValid(long seed) {
        List<String> seedBossRelics = RelicRngSimulator.getInstance().getRelicPools(seed, AbstractRelic.RelicTier.BOSS);
        return bossRelicNames.contains(seedBossRelics.get(this.encounterIndex));
    }
}
