package FilterTheSpire.multithreading;

import FilterTheSpire.RestartHelper;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class SeedSearcher {
    private static final int numThreads = 2;
    private SeedSearcher() { }

    protected static class MainSeedRunnable implements Runnable {
        private boolean running = true;
        private boolean finished = false;
        private ArrayList<SeedSearcherThread> threads = new ArrayList<>();
        private Consumer<Void> onFinish;

        private MainSeedRunnable(Consumer<Void> onFinish) {
            this.onFinish = onFinish;
        }

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
//            finishUp();
            onFinish.accept(null);
        }

//        private void finishUp() {
//            // Find the winning details
//            for (SeedSearcherThread t : threads) {
//                if (t.foundWinningSeed) {
//                    System.out.println("Seed: " + t.seed);
//                    System.out.println("Timestamp: " + t.seedSourceTimestamp);
//                    finished = true;
//                    onFinish.accept(null);
//                    return;
//                }
//            }
//
//            System.out.println("ERROR: Could not find a winning seed!");
//        }

        protected void notifyFinished() { running = false; }
        protected boolean isRunning() { return running; }

        protected String getNumSeedsExamined() {
            int sum = threads.stream().mapToInt(t -> t.seedsExamined).sum();
            return "" + sum;
        }
    }

//    private static ArrayList<SeedSearcherThread> threads = new ArrayList<>();
//
//    private static Runnable mainThread;
//    private static boolean searching = true;

    private static MainSeedRunnable runner;
    private static Thread runnerThread;

    public static void searchForSeed(Consumer<Void> onComplete) {
        runner = new MainSeedRunnable(onComplete);
        runnerThread = new Thread(runner);
        runnerThread.start();
    }

    public String getNumSeedsExamined() {
        return (runner != null) ? runner.getNumSeedsExamined() : "ERROR";
    }

    public static boolean isRunning() {
        return (runner != null) && runner.running;
    }

    public static boolean isFinished() {
        return (runner != null) && runner.finished;
    }

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

                return;
            }
        }

        System.out.println("ERROR: no winning seed found - called before finished?");

    }
//    private boolean isSearching;
//
//    @Override
//    public void run() {
//        isSearching = true;
//
//        // Make child threads
//        threads.add(new SeedSearcherThread(this));
//        threads.add(new SeedSearcherThread(this));
//        threads.forEach(SeedSearcherThread::start);
//
//        // Join the threads
//        try {
//            for (SeedSearcherThread thread : threads)
//                thread.join();
//
//            System.out.println("Joined all threads");
//        }
//        catch(InterruptedException e){
//            e.printStackTrace();
//        }
//    }
//
//    public boolean isSearching() { return isSearching; }
//
//    public static void searchForSeed() {
//        Thread thread = new Thread(new SeedSearcher());
//        thread.start();
//    }

}

/*
public class SeedSearcher {
    private ArrayList<SeedSearcherThread> threads = new ArrayList<>();
    protected Random rng;
    private boolean isCompleted = false;

    public SeedSearcher() {
        Long sTime = System.nanoTime();
        rng = new Random(sTime);
    }

    public void searchForSeed() {
        System.out.println("Seed searcher: making threads");
        threads.add(new SeedSearcherThread(this, rng.randomLong()));
        threads.add(new SeedSearcherThread(this, rng.randomLong()));

        System.out.println("Seed searcher starting threads");
        threads.forEach(SeedSearcherThread::start);
        System.out.println("Seed searcher done making threads");
    }

    protected void notifyComplete() {
        isCompleted = true;

        System.out.println("SeedSearcher: attempting to join threads " + threads.size());
        for (SeedSearcherThread t : threads) {
            System.out.println("SeedSearcher: attempting to join in " + t);
            try {
                t.tid.join();
                System.out.println("successful join " + t);
            } catch (InterruptedException e) {
                System.out.println("failed join " + t);
                e.printStackTrace();
            }
        }

        System.out.println("Threads all joined in finally");
        //applyValidSeed();
    }

    public boolean isCompleted() { return isCompleted; }

    public int getNumChecked() { return threads.stream().mapToInt(SeedSearcherThread::getNum).sum(); }

    public void applyValidSeed() {
        for (SeedSearcherThread t : threads) {
            if (t.foundWinningSeed) {
                System.out.println("Setting seed to " + t.seed);

                Settings.seedSourceTimestamp = t.seedSourceTimestamp;
                Settings.seed = t.seed;
                SeedHelper.cachedSeed = null;

                RestartHelper.makeReal();
                return;
            }
        }
    }

}

 */
