package FilterTheSpire.simulators;

import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.utils.CharacterPool;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import java.util.*;

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

    public String nthColorlessRareCard(long seed, int encounterIndex) {
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARD);
        AbstractCard card = AbstractDungeon.colorlessCardPool.getRandomCard(cardRng, AbstractCard.CardRarity.RARE);
        for(int i = 0; i < encounterIndex; ++i) {
            card = AbstractDungeon.colorlessCardPool.getRandomCard(cardRng, AbstractCard.CardRarity.RARE);
        }
        return card.cardID;
    }

    /**
     * Tries to find a card reward from the Nth combat, this assumes no Question Card or Prismatic or Diverse
     * basically assumes X combats in a row without card rewards in between, so should only be called with very low numbers
     * @param seed: seed as long
     * @param combatIndex: which combat card should appear in (0 = first combat)
     * @param searchCard: which card to search in the card reward
     * @return true if the search cards were found in the combat rewards
     */
    public boolean getNthCardReward(long seed, int combatIndex, String searchCard) {
        int numCardsInReward = 3;
        CharacterPool pool = CharacterPoolFactory.getCharacterPool(AbstractDungeon.player.chosenClass);
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARD);

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

                if (searchCard.equals(cardReward.cardId) && i == combatIndex){
                    return true;
                }

                rewards.add(cardReward);
                containsDupe = true;
            }

            for (CardRewardInfo card: rewards) {
                if (card.rarity != AbstractCard.CardRarity.RARE){
                    cardRng.randomBoolean(0.0F);
                }
            }
        }

        return false;
    }

    private static class CardRewardInfo {
        public String cardId;
        public AbstractCard.CardRarity rarity;

        public CardRewardInfo(String cardId, AbstractCard.CardRarity rarity){
            this.cardId = cardId;
            this.rarity = rarity;
        }
    }
}

