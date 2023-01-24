package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestBossRelic {
    @Test
    public void testKnownPandoraSeed() {
        // This is a known pandora's seed
        long seed = 3271423080934870601L;

        Random rng = new Random(seed);
        List<String> bossRelics = RelicRngSimulator.getInstance().getRelicPools(seed, rng, AbstractPlayer.PlayerClass.IRONCLAD).get(AbstractRelic.RelicTier.BOSS);

        assert(bossRelics.get(0).equals("Pandora's Box"));
        assert(!bossRelics.get(0).equals("Runic Pyramid"));
    }
}
