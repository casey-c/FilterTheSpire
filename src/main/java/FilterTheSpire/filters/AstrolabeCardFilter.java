package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardTransformSimulator;
import FilterTheSpire.utils.helpers.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;

import java.util.HashMap;
import java.util.TreeMap;

public class AstrolabeCardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private int totalTransformCount;
    private TreeMap<AbstractCard.CardRarity, Boolean> cardRaritiesShouldReverse;

    public AstrolabeCardFilter(HashMap<String, Integer> searchCards){
        this.sortOrder = 1;
        this.searchCards = searchCards;
        this.totalTransformCount = 3;

        // generate all card rarities and reverse the common pool
        cardRaritiesShouldReverse = new TreeMap<>();
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.COMMON, true);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.UNCOMMON, false);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.RARE, false);
    }

    public boolean isSeedValid(long seed) {
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MISC);
        cardRng.random();
        return CardTransformSimulator.getInstance().isValid(cardRng, searchCards, totalTransformCount, cardRaritiesShouldReverse);
    }
}
