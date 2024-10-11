package FilterTheSpire.filters;

import FilterTheSpire.simulators.EventSimulator;
import FilterTheSpire.utils.helpers.SeedHelper;
import com.megacrit.cardcrawl.random.Random;

// This should not really be used, there are too many factors in events
// such as current gold, current deck, and current health that we can't necessarily predict
public class Act1EventFilter extends AbstractFilter {
    private String event;

    public Act1EventFilter(String event){
        this.event = event;
    }

    public boolean isSeedValid(long seed) {
        Random rng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.EVENT);
        rng.random();
        String eventKey = EventSimulator.getInstance().generateEvent(rng, 0);
        return eventKey.equals(this.event);
    }

    public String generateHashKey() {
        return null;
    }
}
