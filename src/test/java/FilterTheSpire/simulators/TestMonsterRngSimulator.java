package FilterTheSpire.simulators;

import FilterTheSpire.filters.AbstractFilter;
import FilterTheSpire.filters.BossFilter;
import org.junit.jupiter.api.Test;

import java.util.Collections;


public class TestMonsterRngSimulator {
    private final long seed = 3271423080934870601L;

    @Test
    public void testKnownHexaghostSeed() {
        // This seed is known to have hexaghost as the act 1 boss
        AbstractFilter boss = new BossFilter(Collections.singletonList("Hexaghost"), 1);
        assert(boss.isSeedValid(seed));
    }

    @Test
    public void testKnownCollectorSeed() {
        // This seed is known to have Collector as the act 2 boss
        AbstractFilter boss = new BossFilter(Collections.singletonList("TheCollector"), 2);
        assert(boss.isSeedValid(seed));
    }
}
