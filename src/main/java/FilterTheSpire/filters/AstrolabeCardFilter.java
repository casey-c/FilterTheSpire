package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardTransformSimulator;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.random.Random;

import java.util.HashMap;

public class AstrolabeCardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private int totalTransformCount;

    public AstrolabeCardFilter(HashMap<String, Integer> searchCards){
        this.sortOrder = 1;
        this.searchCards = searchCards;
        this.totalTransformCount = 3;
    }

    public boolean isSeedValid(long seed) {
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MISC);
        cardRng.random();
        return CardTransformSimulator.getInstance().isValid(cardRng, searchCards, totalTransformCount, true);
    }
}
