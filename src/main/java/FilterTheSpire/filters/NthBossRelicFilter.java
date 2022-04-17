package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Collections;
import java.util.List;

public class NthBossRelicFilter extends AbstractFilter{
    private List<String> bossRelicNames;
    private List<Integer> possibleEncounterIndices;

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
