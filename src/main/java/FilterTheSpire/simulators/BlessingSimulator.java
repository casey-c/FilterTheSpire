package FilterTheSpire.simulators;

import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.HashMap;

// This logic can be found in NeowReward.class
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

    public boolean isBlessingValid(long seed, NeowReward.NeowRewardType rewardType, HashMap<String, Integer> searchCards){
        switch (rewardType){
            case THREE_CARDS:
            case ONE_RANDOM_RARE_CARD:
            case REMOVE_CARD:
            case UPGRADE_CARD:
            case TRANSFORM_CARD:
            case RANDOM_COLORLESS:
                return isFirstBlessingValid(seed, rewardType, searchCards);
            case THREE_SMALL_POTIONS:
            case RANDOM_COMMON_RELIC:
            case TEN_PERCENT_HP_BONUS:
            case THREE_ENEMY_KILL:
            case HUNDRED_GOLD:
                return isSecondBlessingValid(seed, rewardType);
            case RANDOM_COLORLESS_2:
            case REMOVE_TWO:
            case ONE_RARE_RELIC:
            case THREE_RARE_CARDS:
            case TWO_FIFTY_GOLD:
            case TRANSFORM_TWO_CARDS:
            case TWENTY_PERCENT_HP_BONUS:
                return isThirdBlessingValid(seed, rewardType, searchCards);
            default:
                throw new IllegalArgumentException("Neow Bonus not found");
        }
    }

    private boolean isFirstBlessingValid(long seed, NeowReward.NeowRewardType rewardType, HashMap<String, Integer> searchCards) {
        Random blessingRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.NEOW);

        ArrayList<NeowReward.NeowRewardType> neowRewardTypes = new ArrayList<>();
        neowRewardTypes.add(NeowReward.NeowRewardType.THREE_CARDS);
        neowRewardTypes.add(NeowReward.NeowRewardType.ONE_RANDOM_RARE_CARD);
        neowRewardTypes.add(NeowReward.NeowRewardType.REMOVE_CARD);
        neowRewardTypes.add(NeowReward.NeowRewardType.UPGRADE_CARD);
        neowRewardTypes.add(NeowReward.NeowRewardType.TRANSFORM_CARD);
        neowRewardTypes.add(NeowReward.NeowRewardType.RANDOM_COLORLESS);

        boolean isValid = blessingRng.random(0, neowRewardTypes.size() - 1) == neowRewardTypes.indexOf(rewardType);
        if (!searchCards.isEmpty()){
            blessingRng.random();
            blessingRng.random();
            blessingRng.random(0, 3);
            if (rewardType == NeowReward.NeowRewardType.TRANSFORM_CARD){
                isValid = isValid && CardTransformSimulator.getInstance().isValid(blessingRng, searchCards, 1);
            } else if (rewardType == NeowReward.NeowRewardType.ONE_RANDOM_RARE_CARD){
                isValid = isValid && searchCards.containsKey(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, blessingRng).cardID);
            }
        }
        return isValid;
    }

    private boolean isSecondBlessingValid(long seed, NeowReward.NeowRewardType rewardType) {
        Random blessingRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.NEOW);
        blessingRng.random(); // Random First Blessing

        ArrayList<NeowReward.NeowRewardType> neowRewardTypes = new ArrayList<>();
        neowRewardTypes.add(NeowReward.NeowRewardType.THREE_SMALL_POTIONS);
        neowRewardTypes.add(NeowReward.NeowRewardType.RANDOM_COMMON_RELIC);
        neowRewardTypes.add(NeowReward.NeowRewardType.TEN_PERCENT_HP_BONUS);
        neowRewardTypes.add(NeowReward.NeowRewardType.THREE_ENEMY_KILL);
        neowRewardTypes.add(NeowReward.NeowRewardType.HUNDRED_GOLD);

        return blessingRng.random(0, neowRewardTypes.size() - 1) == neowRewardTypes.indexOf(rewardType);
    }

    private boolean isThirdBlessingValid(long seed, NeowReward.NeowRewardType rewardType, HashMap<String, Integer> searchCards) {
        Random blessingRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.NEOW);
        blessingRng.random(); // Random First Blessing
        blessingRng.random(); // Random Second Blessing
        int drawbackNum = blessingRng.random(0, 3); // The drawback for the third blessing

        ArrayList<NeowReward.NeowRewardType> neowRewardTypes = new ArrayList<>();
        neowRewardTypes.add(NeowReward.NeowRewardType.RANDOM_COLORLESS_2);
        if (drawbackNum != 2) {
            neowRewardTypes.add(NeowReward.NeowRewardType.REMOVE_TWO);
        }
        neowRewardTypes.add(NeowReward.NeowRewardType.ONE_RARE_RELIC);
        neowRewardTypes.add(NeowReward.NeowRewardType.THREE_RARE_CARDS);
        if (drawbackNum != 1) {
            neowRewardTypes.add(NeowReward.NeowRewardType.TWO_FIFTY_GOLD);
        }
        neowRewardTypes.add(NeowReward.NeowRewardType.TRANSFORM_TWO_CARDS);
        if (drawbackNum != 0){
            neowRewardTypes.add(NeowReward.NeowRewardType.TWENTY_PERCENT_HP_BONUS);
        }

        boolean isValid = blessingRng.random(0, neowRewardTypes.size() - 1) == neowRewardTypes.indexOf(rewardType);
        if (!searchCards.isEmpty() && rewardType == NeowReward.NeowRewardType.TRANSFORM_TWO_CARDS){
            blessingRng.random();
            isValid = isValid && CardTransformSimulator.getInstance().isValid(blessingRng, searchCards, 2);
        }
        return isValid;
    }
}
