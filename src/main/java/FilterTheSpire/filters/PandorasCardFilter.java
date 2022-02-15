package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardTransformSimulator;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

public class PandorasCardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private int pandoraTransforms;

    public PandorasCardFilter(HashMap<String, Integer> searchCards){
        this.sortOrder = 1;
        this.searchCards = searchCards;
        this.pandoraTransforms = AbstractDungeon.player.getStartingDeck().size() -
                ((AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) ? 1 : 2);
    }

    public boolean isSeedValid(long seed) {
        return CardTransformSimulator.getInstance().isValid(seed, searchCards, pandoraTransforms);
    }
}
