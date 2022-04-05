package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardTransformSimulator;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

import java.util.HashMap;

public class PandorasCardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private int totalTransformCount;

    public PandorasCardFilter(HashMap<String, Integer> searchCards){
        this.sortOrder = 2;
        this.searchCards = searchCards;
        this.totalTransformCount = AbstractDungeon.player.getStartingDeck().size() -
                ((AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) ? 1 : 2);
    }

    public boolean isSeedValid(long seed) {
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARDRANDOM);
        return CardTransformSimulator.getInstance().isValid(cardRng, searchCards, totalTransformCount, false);
    }
}
