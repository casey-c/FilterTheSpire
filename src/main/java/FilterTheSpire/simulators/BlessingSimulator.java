package FilterTheSpire.simulators;

import FilterTheSpire.utils.helpers.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

// This logic can be found in NeowReward.class
public class BlessingSimulator {
    private static BlessingSimulator singleton = null;
    private TreeMap<AbstractCard.CardRarity, Boolean> cardRaritiesShouldReverse;

    public static BlessingSimulator getInstance(){
        if (singleton == null){
            singleton = new BlessingSimulator();
        }
        return singleton;
    }

    private BlessingSimulator(){
        // Whenever we choose a transform bonus, generate all card rarities and reverse the common cards
        cardRaritiesShouldReverse = new TreeMap<>();
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.COMMON, true);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.UNCOMMON, false);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.RARE, false);
    }

    public boolean isBlessingValid(long seed, NeowReward.NeowRewardType rewardType, HashMap<String, Integer> searchCards, NeowReward.NeowRewardDrawback drawback){
        HashMap<AbstractRelic.RelicTier, List<String>> relicPools = null;
        if (rewardType == NeowReward.NeowRewardType.RANDOM_COMMON_RELIC || rewardType == NeowReward.NeowRewardType.ONE_RARE_RELIC) {
            relicPools = RelicRngSimulator.getInstance().getRelicPools(seed);
        }
        if (rewardType == null){
            return isThirdBlessingValid(seed, null, searchCards, drawback, null);
        }
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
                return isSecondBlessingValid(seed, rewardType, relicPools);
            case RANDOM_COLORLESS_2:
            case REMOVE_TWO:
            case ONE_RARE_RELIC:
            case THREE_RARE_CARDS:
            case TWO_FIFTY_GOLD:
            case TRANSFORM_TWO_CARDS:
            case TWENTY_PERCENT_HP_BONUS:
                return isThirdBlessingValid(seed, rewardType, searchCards, drawback, relicPools);
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
            switch (rewardType){
                case TRANSFORM_CARD:
                    blessingRng.random();
                    isValid = isValid && CardTransformSimulator.getInstance().isValid(blessingRng, searchCards, 1, cardRaritiesShouldReverse);
                    break;
                case THREE_CARDS:
                    blessingRng.random();
                    isValid = isValid && CardRngSimulator.getInstance().isValidCardRewardFromNeow(blessingRng, false, new ArrayList<>(searchCards.keySet()));
                    break;
                case ONE_RANDOM_RARE_CARD:
                    blessingRng.random();
                    isValid = isValid && searchCards.containsKey(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, blessingRng).cardID);
                    break;
                case RANDOM_COLORLESS:
                    isValid = isValid && CardRngSimulator.getInstance().isValidColorlessCardFromNeow(seed, new ArrayList<>(searchCards.keySet()), AbstractCard.CardRarity.UNCOMMON);
                    break;
            }
        }
        return isValid;
    }

    private boolean isSecondBlessingValid(long seed, NeowReward.NeowRewardType rewardType, HashMap<AbstractRelic.RelicTier, List<String>> relicPools) {
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

    private boolean isThirdBlessingValid(long seed, NeowReward.NeowRewardType rewardType, HashMap<String, Integer> searchCards, NeowReward.NeowRewardDrawback drawback, HashMap<AbstractRelic.RelicTier, List<String>> relicPools) {
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

        boolean isValid = rewardType == null || blessingRng.random(0, neowRewardTypes.size() - 1) == neowRewardTypes.indexOf(rewardType);
        if (drawback != null) {
            isValid = isValid && drawbackNum == (drawback.ordinal() - 1);
        }
        if (rewardType != null && !searchCards.isEmpty()){
            switch (rewardType){
                case THREE_RARE_CARDS:
                    blessingRng.random();
                    isValid = isValid && CardRngSimulator.getInstance().isValidCardRewardFromNeow(blessingRng, true, new ArrayList<>(searchCards.keySet()));
                    break;
                case TRANSFORM_TWO_CARDS:
                    blessingRng.random();
                    isValid = isValid && CardTransformSimulator.getInstance().isValid(blessingRng, searchCards, 2, cardRaritiesShouldReverse);
                    break;
                case RANDOM_COLORLESS_2:
                    isValid = isValid && CardRngSimulator.getInstance().isValidColorlessCardFromNeow(seed, new ArrayList<>(searchCards.keySet()), AbstractCard.CardRarity.RARE);
                    break;
            }
        }
        return isValid;
    }
}
