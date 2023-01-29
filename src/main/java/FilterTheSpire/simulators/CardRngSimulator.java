package FilterTheSpire.simulators;

import FilterTheSpire.FilterManager;
import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.utils.cache.RunInfoCache;
import FilterTheSpire.utils.helpers.CardPoolHelper;
import FilterTheSpire.utils.helpers.CharacterPool;
import FilterTheSpire.utils.helpers.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;

import java.util.*;
import java.util.stream.Collectors;

public class CardRngSimulator {
    private static CardRngSimulator singleton = null;

    public static CardRngSimulator getInstance(){
        if (singleton == null){
            singleton = new CardRngSimulator();
        }
        return singleton;
    }

    private CardRngSimulator(){

    }

    public boolean isValidColorlessCardFromNeow(long seed, List<String> searchCards, AbstractCard.CardRarity rarity) {
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARD);
        int numCardsInReward = 3;
        List<String> cardRewards = new ArrayList<>();
        List<String> pool;
        if (rarity == AbstractCard.CardRarity.UNCOMMON) {
            pool = CardPoolHelper.getUncommonColorlessCards();
        } else if (rarity == AbstractCard.CardRarity.RARE) {
            pool = CardPoolHelper.getRareColorlessCards();
        } else {
            throw new IllegalArgumentException();
        }
        ArrayList<String> cards = new ArrayList<>(pool);
        Collections.sort(cards);
        for(int i = 0; i < numCardsInReward; ++i) {
            cardRewards.add(cards.get(cardRng.random(cards.size() - 1)));
        }
        return cardRewards.containsAll(searchCards);
    }

    /**
     * Tries to find a card reward from the Nth combat, this assumes no Question Card or Prismatic or Diverse
     * basically assumes X combats in a row without card rewards in between, so should only be called with very low numbers
     * @param seed: seed as long
     * @param combatIndex: which combat card should appear in (0 = first combat)
     * @param searchCards: which cards to search in the card reward
     * @return true if the search cards were found in the combat rewards
     */
    public boolean isNthCardRewardValid(long seed, int combatIndex, List<String> searchCards) {
        if (searchCards.isEmpty()) {
            return true;
        }

        int numCardsInReward = 3;
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARD);
        CharacterPool pool = CharacterPoolFactory.getCharacterPool(RunInfoCache.currentCharacter);
        if (FilterManager.preRngCounters.containsKey(SeedHelper.RNGType.CARD)){
            cardRng.setCounter(FilterManager.preRngCounters.getOrDefault(SeedHelper.RNGType.CARD, 0));
        }

        boolean containsDupe = true;
        CardRewardInfo cardReward = null;

        int cardBlizzRandomizer = 5;
        for (int i = 0; i <= combatIndex; i++) {
            ArrayList<CardRewardInfo> rewards = new ArrayList<>();
            for (int j = 0; j < numCardsInReward; j++) {
                int randomRoll = cardRng.random(99);
                randomRoll += cardBlizzRandomizer;
                AbstractCard.CardRarity rarity = getCardRarity(randomRoll);

                switch (rarity){
                    case COMMON:
                        cardBlizzRandomizer -= 1;
                        if (cardBlizzRandomizer <= -40) {
                            cardBlizzRandomizer = -40;
                        }
                        break;
                    case RARE:
                        cardBlizzRandomizer = 5;
                        break;
                }

                while(containsDupe) {
                    containsDupe = false;
                    cardReward = new CardRewardInfo(pool.getCard(rarity, cardRng), rarity);

                    for (CardRewardInfo c: rewards) {
                        if (c.cardId.equals(cardReward.cardId)) {
                            containsDupe = true;
                            break;
                        }
                    }
                }

                rewards.add(cardReward);
                containsDupe = true;
            }

            List<String> cardIds = rewards.stream().map(c -> c.cardId).collect(Collectors.toList());
            if (cardIds.containsAll(searchCards)){
                return true;
            }

            for (CardRewardInfo card: rewards) {
                if (card.rarity != AbstractCard.CardRarity.RARE){
                    cardRng.randomBoolean(0.0F);
                }
            }
        }

        return false;
    }

    public boolean isValidCardRewardFromNeow(Random rng, boolean isRareOnly, List<String> searchCards) {
        final int numCardsInReward = 3;
        ArrayList<String> rewardCards = new ArrayList<>();
        for(int i = 0; i < numCardsInReward; ++i) {
            AbstractCard.CardRarity rarity = rng.randomBoolean(0.33F) ? AbstractCard.CardRarity.UNCOMMON : AbstractCard.CardRarity.COMMON;
            if (isRareOnly) {
                rarity = AbstractCard.CardRarity.RARE;
            }

            String card = neowGetCard(rng, rarity);

            while(rewardCards.contains(card)) {
                card = neowGetCard(rng, rarity);
            }

            rewardCards.add(card);
        }

        return rewardCards.containsAll(searchCards);
    }

    private static class CardRewardInfo {
        public String cardId;
        public AbstractCard.CardRarity rarity;

        public CardRewardInfo(String cardId, AbstractCard.CardRarity rarity){
            this.cardId = cardId;
            this.rarity = rarity;
        }
    }

    private String neowGetCard(Random rng, AbstractCard.CardRarity rarity){
        TreeMap<AbstractCard.CardRarity, Boolean> rarityMap = new TreeMap<>();
        rarityMap.put(rarity, true);
        List<String> cardPool = CharacterPoolFactory.getCardPool(RunInfoCache.currentCharacter, rarityMap);
        return cardPool.get(rng.random(cardPool.size() - 1));
    }

    private AbstractCard.CardRarity getCardRarity(int roll) {
        int rareCardChance = 3;
        int uncommonCardChance = 37;

        if (roll < rareCardChance) {
            return AbstractCard.CardRarity.RARE;
        } else if (roll >= rareCardChance + uncommonCardChance) {
            return AbstractCard.CardRarity.COMMON;
        } else {
            return AbstractCard.CardRarity.UNCOMMON;
        }
    }
}

