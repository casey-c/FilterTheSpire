package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardRngSimulator {
    private long seed;

    public CardRngSimulator(long seed) {
        this.seed = seed;
    }

    // Warning: This code might not be thread-safe.
    public String firstColorlessRareCardIs() {
        AbstractDungeon.cardRng = new Random(seed);
        Random cardRng = AbstractDungeon.cardRng.copy();
        AbstractCard card = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE);
        AbstractDungeon.cardRng = cardRng;

        return card.cardID;
    }
}

