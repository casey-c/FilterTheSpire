package FilterTheSpire.filters.bossrelic;

import FilterTheSpire.filters.NthBossRelicFilter;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class TestBossRelic {
    @Test
    public void testKnownPandoraSeed() {
        // This is a known pandora's seed
        long seed = 3271423080934870601L;

        NthBossRelicFilter pandoraFilter = new NthBossRelicFilter(Collections.singletonList("Pandora's Box"), 0);
        NthBossRelicFilter runicFilter = new NthBossRelicFilter(Collections.singletonList("Runic Pyramid"), 0);

        assert(pandoraFilter.isSeedValid(seed));
        assert(!runicFilter.isSeedValid(seed));
    }
}
