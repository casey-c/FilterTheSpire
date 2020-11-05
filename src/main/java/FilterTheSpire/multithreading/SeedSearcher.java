package FilterTheSpire.multithreading;

import FilterTheSpire.utils.RestartHelper;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.concurrent.*;

public class SeedSearcher {
    // TODO: make this depend on the user's OS
    private static final int numThreads = 2;
    private SeedSearcher() { }

    // --------------------------------------------------------------------------------

    // This runner thread is responsible for initiating and joining all the child threads - the children do the work
    //    testing all the seeds. This thread is responsible for making them all quit once the target is found by any of
    //    the children.
    protected static class MainSeedRunnable implements Runnable {
        private boolean running = true;
        private boolean finished = false;
        private ArrayList<SeedSearcherThread> threads = new ArrayList<>();

        @Override
        public void run() {
            Random rng = new Random(System.nanoTime());

            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            for (int i = 0; i < numThreads; ++i)
                threads.add(new SeedSearcherThread(this, rng.randomLong()));

            CompletableFuture<?>[] futures = threads.stream()
                    .map(task -> CompletableFuture.runAsync(task, executor))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join();
            executor.shutdown();

            System.out.println("All tasks completed.");
            finished = true;
        }


        protected void notifyFinished() { running = false; }
        protected boolean isRunning() { return running; }

        protected String getNumSeedsExamined() {
            long sum = threads.stream().mapToLong(t -> t.seedsExamined).sum();
            if (sum <= numThreads)
                sum = 1;
            return "" + sum;
        }
    }

    private static MainSeedRunnable runner;
    private static Thread runnerThread;

    // --------------------------------------------------------------------------------

    // The main starting point of the searching algorithm - launches a new thread to handle further child threads
    public static void searchForSeed() {
        runner = new MainSeedRunnable();
        runnerThread = new Thread(runner);
        runnerThread.start();
    }

    // --------------------------------------------------------------------------------

    // Info getters (for main StS thread to hook into)
    public static String getNumSeedsExamined() { return (runner != null) ? runner.getNumSeedsExamined() : "ERROR"; }
    public static boolean isFinished() { return (runner != null) && runner.finished; }

    // --------------------------------------------------------------------------------

    // Makes the found seed into a "real" one - by letting the game know it is the seed to use and forcing a restart
    public static void makeSeedReal() {
        if (runner == null)
            return;

        for (SeedSearcherThread t : runner.threads) {
            if (t.foundWinningSeed) {
                System.out.println("Updating the seed to match a winning result:");
                System.out.println("Seed: " + SeedHelper.getString(t.seed));
                System.out.println("Timestamp: " + t.seedSourceTimestamp);

                Settings.seed = t.seed;
                Settings.seedSourceTimestamp = t.seedSourceTimestamp;
                SeedHelper.cachedSeed = null;

                RestartHelper.restart();

                return;
            }
        }

        System.out.println("ERROR: no winning seed found - called before finished?");
    }
}

