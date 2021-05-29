package FilterTheSpire.filters.boss;

import FilterTheSpire.filters.BossFilter;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class TestBoss {
    @Test
    public void testKnownHexaghostSeed() {
        // This seed is known to have hexaghost as the act 1 boss
        long seed = 3271423080934870601L;

        BossFilter hexaghost = new BossFilter(Collections.singletonList("Hexaghost"));

        assert(hexaghost.isSeedValid(seed));
    }
}
