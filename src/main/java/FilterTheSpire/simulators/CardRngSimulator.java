package FilterTheSpire.simulators;

import FilterTheSpire.FilterManager;
import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.utils.CharacterPool;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

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
        int numCardsInReward = 3;
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARD);
        List<String> cardRewards = new ArrayList<>();
        for(int i = 0; i < numCardsInReward; ++i) {
            cardRewards.add(AbstractDungeon.colorlessCardPool.getRandomCard(cardRng, rarity).cardID);
        }
        cardRewards.retainAll(searchCards);
        return cardRewards.size() > 0;
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

        CharacterPool pool = CharacterPoolFactory.getCharacterPool(AbstractDungeon.player.chosenClass);
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARD);

        if (FilterManager.preRngCounters.containsKey(SeedHelper.RNGType.CARD)){
            cardRng.setCounter(FilterManager.preRngCounters.getOrDefault(SeedHelper.RNGType.CARD, 0));
        }

        boolean containsDupe = true;
        CardRewardInfo cardReward = null;

        int cardBlizzRandomizer = AbstractDungeon.cardBlizzRandomizer;
        MonsterRoom room = new MonsterRoom();
        for (int i = 0; i <= combatIndex; i++) {
            ArrayList<CardRewardInfo> rewards = new ArrayList<>();
            for (int j = 0; j < numCardsInReward; j++) {
                int random = cardRng.random(99);
                random += cardBlizzRandomizer;
                AbstractCard.CardRarity rarity = room.getCardRarity(random);

                switch (rarity){
                    case COMMON:
                        cardBlizzRandomizer -= AbstractDungeon.cardBlizzGrowth;
                        if (cardBlizzRandomizer <= AbstractDungeon.cardBlizzMaxOffset) {
                            cardBlizzRandomizer = AbstractDungeon.cardBlizzMaxOffset;
                        }
                        break;
                    case RARE:
                        cardBlizzRandomizer = AbstractDungeon.cardBlizzStartOffset;
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
        CharacterPool pool = CharacterPoolFactory.getCharacterPool(AbstractDungeon.player.chosenClass);

        ArrayList<String> rewardCards = new ArrayList<>();
        for(int i = 0; i < numCardsInReward; ++i) {
            AbstractCard.CardRarity rarity = rng.randomBoolean(0.33F) ? AbstractCard.CardRarity.UNCOMMON : AbstractCard.CardRarity.COMMON;
            if (isRareOnly) {
                rarity = AbstractCard.CardRarity.RARE;
            }

            String card = neowGetCard(rng, pool, rarity);

            while(rewardCards.contains(card)) {
                card = neowGetCard(rng, pool, rarity);
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

    private String neowGetCard(Random rng, CharacterPool pool, AbstractCard.CardRarity rarity){
        String card = null;
        switch(rarity) {
            case RARE:
                card = pool.getReversedRareCardPool().get(rng.random(pool.getReversedRareCardPool().size() - 1));
                break;
            case UNCOMMON:
                card = pool.getReversedUncommonCardPool().get(rng.random(pool.getReversedUncommonCardPool().size() - 1));
                break;
            case COMMON:
                card = pool.getReversedCommonCardPool().get(rng.random(pool.getReversedCommonCardPool().size() - 1));
                break;
        }
        return card;
    }
}

