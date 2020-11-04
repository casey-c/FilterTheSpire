package FilterTheSpire.multithreading;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;

public class SeedSearcherThread implements Runnable {
    private int num = 0;

    private static int globalID = 0;
    private int id;

    private Thread tid;
    private SeedSearcher parent;
    private Long offset;

    public SeedSearcherThread(SeedSearcher parent, Long offset) {
        this.id = globalID++;
        this.parent = parent;
        this.offset = offset;
    }

    public void start() {
        tid = new Thread(this);
        tid.start();
    }

    protected int getNum() {
        return num;
    }

    private boolean testRandomSeed() {
        Long sTime = System.nanoTime() + offset;
        Random rng = new Random(sTime);

        long seedSourceTimestamp = sTime + offset;
        long seed = SeedHelper.generateUnoffensiveSeed(rng);

        // Output
        boolean valid = checkBossRelic(seed, "Pandora's Box");
        String seedString = SeedHelper.getString(seed);

        System.out.println("Thread " + id + " checked seed " + seedString + " and it was " + valid);

        return valid;

//        Settings.seedSourceTimestamp = sTime;
//        Settings.seed = SeedHelper.generateUnoffensiveSeed(rng);
//        SeedHelper.cachedSeed = null;
    }

    private boolean checkBossRelic(long seed, String targetRelic) {
        Random relicRng = new Random(seed);

        // Skip past all these
        relicRng.randomLong(); // common
        relicRng.randomLong(); // uncommon
        relicRng.randomLong(); // rare
        relicRng.randomLong(); // shop
        //relicRng.randomLong(); // boss <- this is the one needed (we perform it below)

        ArrayList<String> bossRelicPool = new ArrayList<>();
        RelicLibrary.populateRelicPool(bossRelicPool, AbstractRelic.RelicTier.BOSS, AbstractDungeon.player.chosenClass);
        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));

        return !bossRelicPool.isEmpty() && bossRelicPool.get(0) == targetRelic;
    }

    @Override
    public void run() {
        while (!parent.isCompleted()) {
            if (num == 100) {
                System.out.println("Thread " + id + " reached 100!");
                parent.notifyComplete();
                break;
            }

            //System.out.println("Thread " + id + ": " + num);
            if (testRandomSeed()) {
                parent.notifyComplete();;
                System.out.println("Thread " + id + " found a valid seed after " + num);
                break;
            }

            ++num;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
