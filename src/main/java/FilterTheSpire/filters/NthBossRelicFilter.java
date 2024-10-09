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
        initialize();
        this.bossRelicNames = bossRelicNames;
        this.possibleEncounterIndices = Collections.singletonList(0); // Get the first Boss Relic if no index is specified.
    }

    public NthBossRelicFilter(List<String> bossRelicNames, int encounterIndex) {
        initialize();
        this.bossRelicNames = bossRelicNames;
        this.possibleEncounterIndices = Collections.singletonList(encounterIndex);
    }

    // Since you get shown 3 Boss relics at once, this gives a wider window if you want it from a specific boss
    public NthBossRelicFilter(List<String> bossRelicNames, List<Integer> possibleEncounterIndices) {
        initialize();
        type = FilterType.NthBossRelic;
        this.bossRelicNames = bossRelicNames;
        this.possibleEncounterIndices = possibleEncounterIndices;
    }

    private void initialize(){
        type = FilterType.NthBossRelic;
        displayName = "Boss Relic Filter";
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

    public boolean shouldDisplay() {
        return bossRelicNames.size() > 0;
    }

    public String toString() {
        if (shouldDisplay()){
            return bossRelicNames.toString();
        }
        return "";
    }
}
