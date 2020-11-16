package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.random.Random;

public class BlessingSimulator {
    private long seed;

    public BlessingSimulator(long seed) {
        this.seed = seed;
    }

    public boolean isThirdBlessingColorlessCard() {
        Random blessingRng = new Random(seed);
        blessingRng.random(); // Random First Blessing
        blessingRng.random(); // Random Second Blessing
        int drawbackNum = blessingRng.random(0, 3); // The drawback for the third blessing
        int max = (drawbackNum == 3) ? 6 : 5; // Some drawbacks lack some rewards (can't get curse and card removal)

        return blessingRng.random(0, max) == 0;
    }
}
