package FilterTheSpire.filters;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.simulators.RelicRngSimulator;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.types.RunCheckpoint;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NthBossRelicFilter extends AbstractFilter {
    public final List<String> bossRelicNames;
    public RunCheckpoint pickupCheckpoint;

    public NthBossRelicFilter(List<String> bossRelicNames, RunCheckpoint checkpoint) {
        initialize();
        type = FilterType.NthBossRelic;
        this.bossRelicNames = bossRelicNames;
        this.pickupCheckpoint = checkpoint;
    }

    private void initialize(){
        type = FilterType.NthBossRelic;
        displayName = "Boss Relic Filter";
    }

    public boolean isSeedValid(long seed) {
        List<String> seedBossRelics = RelicRngSimulator.getInstance().getRelicPools(seed, AbstractRelic.RelicTier.BOSS);
        for (int encounterIndex: getEncounterIndicesFromPickup()) {
            if (bossRelicNames.contains(seedBossRelics.get(encounterIndex))) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldDisplay() {
        return bossRelicNames.size() > 0;
    }

    public String generateHashKey() {
        return FilterTheSpire.config.generateHashKey(type, Collections.singletonList(pickupCheckpoint.ordinal()));
    }

    public String toString() {
        if (shouldDisplay()){
            return bossRelicNames.toString() + " from " + pickupCheckpoint;
        }
        return "";
    }

    private List<Integer> getEncounterIndicesFromPickup(){
        // The boss relic indices depend on if the user has a boss swap or not
        ArrayList<AbstractFilter> filterList = FilterManager.getFilters();
        boolean hasBossSwap = filterList
                        .stream()
                        .filter(f -> f.type == FilterType.NthBossRelic && f != this)
                        .map(f -> (NthBossRelicFilter) f)
                        .anyMatch(f -> f.pickupCheckpoint == RunCheckpoint.NEOW);
        switch (pickupCheckpoint){
            case NEOW:
                return Collections.singletonList(0);
            case ACT1_BOSS:
                if (hasBossSwap){
                    return Arrays.asList(1, 2, 3);
                }
                return Arrays.asList(0, 1, 2);
            case ACT2_BOSS:
                if (hasBossSwap){
                    return Arrays.asList(4, 5, 6);
                }
                return Arrays.asList(3, 4, 5);
            case ACT3_BOSS:
                if (hasBossSwap){
                    return Arrays.asList(7, 8, 9);
                }
                return Arrays.asList(6, 7, 8);
        }
        return Collections.singletonList(0);
    }
}
