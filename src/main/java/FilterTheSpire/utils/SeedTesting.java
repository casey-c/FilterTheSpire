package FilterTheSpire.utils;

import FilterTheSpire.rng.MonsterRngHelper;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;

// TODO: DEBUG ONLY / REMOVE
public class SeedTesting {

    public static void bossTest() {
        MonsterRngHelper helper = new MonsterRngHelper(Settings.seed);
        helper.print();
    }

}
