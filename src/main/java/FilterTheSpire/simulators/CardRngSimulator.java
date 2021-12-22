package FilterTheSpire.simulators;

import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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
}

