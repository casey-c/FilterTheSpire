package FilterTheSpire.simulators;

import FilterTheSpire.factory.MonsterListHelperFactory;
import com.megacrit.cardcrawl.random.Random;
import org.junit.jupiter.api.Test;


public class TestBoss {
    @Test
    public void testKnownHexaghostSeed() {
        // This seed is known to have hexaghost as the act 1 boss
        long seed = 3271423080934870601L;
        Random rng = new Random(seed);

        String boss = MonsterRngSimulator.getInstance().firstBoss(rng, MonsterListHelperFactory.getMonsterListHelperFromActNumber(1));
        assert(boss.equals("Hexaghost"));
    }
}
