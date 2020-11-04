package FilterTheSpire.multithreading;

import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

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
    }

    public boolean isCompleted() { return isCompleted; }

    public int getNumChecked() {
        return threads.stream().mapToInt(SeedSearcherThread::getNum).sum();
    }

}
