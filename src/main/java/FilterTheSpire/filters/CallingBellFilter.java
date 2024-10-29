package FilterTheSpire.filters;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.FilterManager;
import FilterTheSpire.simulators.RelicRngSimulator;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.types.RunCheckpoint;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CallingBell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CallingBellFilter extends AbstractFilter {
    private String commonRelic = null;
    private String uncommonRelic = null;
    private String rareRelic = null;

    public CallingBellFilter(List<String> relics) {
        type = FilterType.CallingBellRelic;
        this.displayName = "Calling Bell Relic Filter";
        if (relics.size() > 0){
            if (relics.get(0) != null){
                this.commonRelic = relics.get(0);
            }
            if (relics.get(1) != null){
                this.uncommonRelic = relics.get(1);
            }
            if (relics.get(2) != null){
                this.rareRelic = relics.get(2);
            }
        }
    }

    public boolean isSeedValid(long seed) {
        ArrayList<AbstractFilter> filterList = FilterManager.getFilters();
        boolean hasCallingBellFilter = filterList
                .stream()
                .filter(f -> f.type == FilterType.NthBossRelic)
                .map(f -> (NthBossRelicFilter) f)
                .anyMatch(f -> f.bossRelicNames.contains(CallingBell.ID) && f.pickupCheckpoint == RunCheckpoint.NEOW);

        if (hasCallingBellFilter){
            HashMap<AbstractRelic.RelicTier, List<String>> relicPools = RelicRngSimulator.getInstance().getRelicPools(seed);
            boolean hasCommonRelic = true;
            if (commonRelic != null){
                hasCommonRelic = relicPools.get(AbstractRelic.RelicTier.COMMON).get(0).equals(commonRelic);
            }
            boolean hasUncommonRelic = true;
            if (uncommonRelic != null){
                hasUncommonRelic = relicPools.get(AbstractRelic.RelicTier.UNCOMMON).get(0).equals(uncommonRelic);
            }
            boolean hasRareRelic = true;
            if (rareRelic != null){
                hasRareRelic = relicPools.get(AbstractRelic.RelicTier.RARE).get(0).equals(rareRelic);
            }
            return hasCommonRelic && hasUncommonRelic && hasRareRelic;
        }
        return true;
    }

    public boolean shouldDisplay(){
        return commonRelic != null || uncommonRelic != null || rareRelic != null;
    }


    public String generateHashKey() {
        return FilterTheSpire.config.generateHashKey(type, Collections.singletonList(0));
    }

    public String toString(){
        return "Common Relic: " + commonRelic + ", Uncommon Relic: " + uncommonRelic + ", Rare Relic: " + rareRelic;
    }
}
