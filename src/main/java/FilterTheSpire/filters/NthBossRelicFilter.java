package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;
import FilterTheSpire.utils.config.FilterType;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Collections;
import java.util.List;

public class NthBossRelicFilter extends AbstractFilter {
    private final List<String> bossRelicNames;
    private final List<Integer> possibleEncounterIndices;

    public NthBossRelicFilter(List<String> bossRelicNames) {
        this.bossRelicNames = bossRelicNames;
        this.possibleEncounterIndices = Collections.singletonList(0); // Get the first Boss Relic if no index is specified.
    }

    public NthBossRelicFilter(List<String> bossRelicNames, int encounterIndex) {
        this.bossRelicNames = bossRelicNames;
        this.possibleEncounterIndices = Collections.singletonList(encounterIndex);
    }

    // Since you get shown 3 Boss relics at once, this gives a wider window if you want it from a specific boss
    public NthBossRelicFilter(List<String> bossRelicNames, List<Integer> possibleEncounterIndices) {
        type = FilterType.NthBossRelic;
        this.bossRelicNames = bossRelicNames;
        this.possibleEncounterIndices = possibleEncounterIndices;
    }

    public boolean isSeedValid(long seed) {
        List<String> seedBossRelics = RelicRngSimulator.getInstance().getRelicPools(seed, AbstractRelic.RelicTier.BOSS);
        for (int encounterIndex: this.possibleEncounterIndices) {
            if (bossRelicNames.contains(seedBossRelics.get(encounterIndex))) {
                return true;
            }
        }
        return false;
    }
}
