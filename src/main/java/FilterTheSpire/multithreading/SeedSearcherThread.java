package FilterTheSpire.multithreading;

import FilterTheSpire.FilterManager;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.random.Random;

public class SeedSearcherThread extends Thread {
    private SeedSearcher.MainSeedRunnable parent;
    private long offset;

    protected int seedsExamined;

    protected boolean foundWinningSeed = false;
    protected long seedSourceTimestamp;
    protected long seed;

    private static int globalID = 0;
    protected int id;


    public SeedSearcherThread(SeedSearcher.MainSeedRunnable parent, long offset) {
        this.parent = parent;
        this.offset = offset;
        this.id = globalID++;

        System.out.println("Made thread " + id);
    }

    @Override
    public void run() {
        while(parent.isRunning()) {
            ++seedsExamined;

            if (tryRandomSeed()) {
                System.out.println("Thread " + id + " found winning seed after " + seedsExamined);
                this.foundWinningSeed = true;
                parent.notifyFinished();
            }


            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean tryRandomSeed() {
        Long sTime = System.nanoTime() + offset;
        Random rng = new Random(sTime);

        seedSourceTimestamp = sTime + offset;
        seed = SeedHelper.generateUnoffensiveSeed(rng);

        boolean valid = FilterManager.validateFilters(seed);
        System.out.println("Thread " + id + " testing seed " + SeedHelper.getString(seed) + ": " + valid);
        return valid;

        //return FilterManager.validateFilters(seed);
    }


}

/*
public class SeedSearcherThread implements Runnable {
    private int num = 0;

    private static int globalID = 0;
    private int id;

    protected Thread tid;
    private SeedSearcher parent;
    private Long offset;

    protected boolean foundWinningSeed = false;
    protected long seedSourceTimestamp;
    protected long seed;

    public SeedSearcherThread(SeedSearcher parent, Long offset) {
        this.id = globalID++;
        this.parent = parent;
        this.offset = offset;

        System.out.println("Made thread " + id);
    }

    public void start() {
        tid = new Thread(this);
        tid.start();
    }

//    public void join() {
//        System.out.println("Thread " + id + " is joining.");
//        try {
//            tid.join();
//            System.out.println("Thread " + id + " joined successfully");
//        } catch (InterruptedException e) {
//            System.out.println("Thread " + id + " FAILED to join");
//            e.printStackTrace();
//        }
//
//    }

    protected int getNum() {
        return num;
    }

    private boolean testRandomSeed() {
        Long sTime = System.nanoTime() + offset;
        Random rng = new Random(sTime);

        seedSourceTimestamp = sTime + offset;
        seed = SeedHelper.generateUnoffensiveSeed(rng);

        return FilterManager.validateFilters(seed);

        // Output
        //boolean valid = checkBossRelic(seed, "Pandora's Box");
//        boolean valid = FilterManager.validateFilters(seed);
//
////        String seedString = SeedHelper.getString(seed);
////        System.out.println("Thread " + id + " checked seed " + seedString + " and it was " + valid);
//
//        return valid;

//        Settings.seedSourceTimestamp = sTime;
//        Settings.seed = SeedHelper.generateUnoffensiveSeed(rng);
//        SeedHelper.cachedSeed = null;
    }

//    private boolean checkBossRelic(long seed, String targetRelic) {
//        Random relicRng = new Random(seed);
//
//        // Skip past all these
//        relicRng.randomLong(); // common
//        relicRng.randomLong(); // uncommon
//        relicRng.randomLong(); // rare
//        relicRng.randomLong(); // shop
//        //relicRng.randomLong(); // boss <- this is the one needed (we perform it below)
//
//        ArrayList<String> bossRelicPool = new ArrayList<>();
//        RelicLibrary.populateRelicPool(bossRelicPool, AbstractRelic.RelicTier.BOSS, AbstractDungeon.player.chosenClass);
//        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));
//
//        return !bossRelicPool.isEmpty() && bossRelicPool.get(0) == targetRelic;
//    }

    @Override
    public void run() {
        while (!parent.isCompleted()) {
            System.out.println("Thread " + id + " is working....");

            if (num == 100) {
                System.out.println("Thread " + id + " reached 100!");
                parent.notifyComplete();
                break;
            }

            //System.out.println("Thread " + id + ": " + num);
            if (testRandomSeed()) {
                foundWinningSeed = true;

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

        System.out.println("Thread " + id + " has exited its while loop | find a valid seed?: " + foundWinningSeed);

        try {
            System.out.println("Thread " + id + " attempting to join");
            tid.join();
            System.out.println("Thread " + id + " successful join");
        } catch (InterruptedException e) {
            System.out.println("Thread " + id + " failed join");
            e.printStackTrace();
        }

    }
}

 */
