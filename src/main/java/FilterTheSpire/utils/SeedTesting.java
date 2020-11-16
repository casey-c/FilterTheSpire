package FilterTheSpire.utils;

import FilterTheSpire.simulators.MonsterRngSimulator;
import com.megacrit.cardcrawl.core.Settings;

// TODO: DEBUG ONLY / REMOVE
public class SeedTesting {
    public static void bossTest() {
        MonsterRngSimulator helper = new MonsterRngSimulator(Settings.seed);
        helper.print();
    }
}
