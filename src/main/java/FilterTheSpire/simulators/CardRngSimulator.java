package FilterTheSpire.simulators;

import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.utils.CharacterPool;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
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

    public boolean getNthCardReward(long seed, List<Integer> encounterIndices, HashMap<String, Integer> searchCards) {
        CharacterPool pool = CharacterPoolFactory.getCharacterPool(AbstractDungeon.player.chosenClass);
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARD);
        int maxEncounter = Collections.max(encounterIndices);
        ArrayList<String> rewards = new ArrayList<>();
        boolean containsDupe = true;
        String cardReward = null;

        Set<String> cardList = searchCards.keySet();
        HashMap<String, Integer> dropCounts = new HashMap<>();
        for (String cardId: cardList) {
            dropCounts.put(cardId, 0);
        }

        MonsterRoom room = new MonsterRoom();
        for (int i = 0; i <= maxEncounter; i++){
            // I think this doesn't work because the rarity depends on the current room node
            int random = cardRng.random(99);
            random += AbstractDungeon.cardBlizzRandomizer;
            room.getCardRarity(random);
            AbstractCard.CardRarity rarity = AbstractDungeon.rollRarity(cardRng);

            while(containsDupe) {
                containsDupe = false;
                cardReward = pool.getCard(rarity, cardRng);

                for (String c: rewards) {
                    if (c.equals(cardReward)) {
                        containsDupe = true;
                        break;
                    }
                }
            }

            if (cardReward != null) {
                rewards.add(cardReward);
                if (cardList.contains(cardReward)){
                    dropCounts.computeIfPresent(cardReward, (k, v) -> v + 1);
                }
            }

            containsDupe = true;
        }

        boolean isValid = true;
        for (String cardId : cardList) {
            if (searchCards.get(cardId) > dropCounts.get(cardId)) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }
}

