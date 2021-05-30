package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.random.Random;

public class BlessingSimulator {
    private static BlessingSimulator singleton = null;

    public static BlessingSimulator getInstance(){
        if (singleton == null){
            singleton = new BlessingSimulator();
        }
        return singleton;
    }

    private BlessingSimulator(){

    }

    /**
     * This logic can be found in NeowReward.class
     * @param seed - used for the RNG
     * @param rewardType - the Neow Bonus we want
     * @return if the seed's Neow Bonus is the same as the one passed in
     */
    public boolean isThirdBlessingValid(long seed, NeowReward.NeowRewardType rewardType) {
        Random blessingRng = new Random(seed);
        blessingRng.random(); // Random First Blessing
        blessingRng.random(); // Random Second Blessing
        int drawbackNum = blessingRng.random(0, 3); // The drawback for the third blessing
        int max = (drawbackNum == 3) ? 6 : 5; // Some drawbacks lack some rewards (can't get curse and card removal)
        int index;
        switch (rewardType){
            case RANDOM_COLORLESS_2:
                index = 0;
                break;
            case REMOVE_TWO:
                index = drawbackNum == 2 ? -1 : 1;
                break;
            case ONE_RARE_RELIC:
                index = drawbackNum == 2 ? 1 : 2;
                break;
            case THREE_RARE_CARDS:
                index = drawbackNum == 2 ? 2 : 3;
                break;
            case TWO_FIFTY_GOLD:
                if (drawbackNum == 1){
                    index = -1;
                } else if (drawbackNum == 2) {
                    index = 3;
                } else {
                    index = 4;
                }
                break;
            case TRANSFORM_TWO_CARDS:
                if (drawbackNum == 1 || drawbackNum == 2){
                    index = 4;
                } else {
                    index = 5;
                }
                break;
            case TWENTY_PERCENT_HP_BONUS:
                if (drawbackNum == 0){
                    index = -1;
                } else if (drawbackNum == 1 || drawbackNum == 2) {
                    index = 5;
                } else {
                    index = 6;
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        return blessingRng.random(0, max) == index;
    }
}
