package FilterTheSpire.multithreading;

import FilterTheSpire.FilterManager;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.random.Random;

public class SeedSearcherThread extends Thread {
    private SeedSearcher.MainSeedRunnable parent;
    private long offset;

    protected long seedsExamined;

    protected boolean foundWinningSeed = false;
    protected long seedSourceTimestamp;
    protected long seed;

    private static int globalID = 0;
    protected int id;


    public SeedSearcherThread(SeedSearcher.MainSeedRunnable parent, long offset) {
        this.parent = parent;
        this.offset = offset;
        this.id = globalID++;
    }

    @Override
    public void run() {
        FilterManager.sortFilters();

        while(parent.isRunning()) {
            ++seedsExamined;

            if (tryRandomSeed()) {
                this.foundWinningSeed = true;
                parent.notifyFinished();
            }

            // Unironically kind of like having the delay because it makes the animations cooler lol
            // Without it, there's literally no animation in the base use cases as it goes too fast and finishes in a
            // split second

            // TODO: put this behind a config option. (I'm leaving it now because I can and you can't stop me)
//            try {
//                sleep(20);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    private boolean tryRandomSeed() {
        Long sTime = System.nanoTime() + offset;
        Random rng = new Random(sTime);

        seedSourceTimestamp = sTime + offset;
        seed = SeedHelper.generateUnoffensiveSeed(rng);

        return FilterManager.validateFilters(seed);
    }


}

