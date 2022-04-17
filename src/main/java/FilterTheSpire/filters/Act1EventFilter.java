package FilterTheSpire.filters;

import FilterTheSpire.patches.EventHelperPatch;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

public class Act1EventFilter extends AbstractFilter {
    private String event;

    public Act1EventFilter(String event){
        this.event = event;
    }

    public boolean isSeedValid(long seed) {
        Random rng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.EVENT);
        AbstractDungeon.generateEvent(rng);
        return EventHelperPatch.eventName.equals(this.event);
    }
}
